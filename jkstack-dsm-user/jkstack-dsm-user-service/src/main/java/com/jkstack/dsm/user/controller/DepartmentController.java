package com.jkstack.dsm.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jkstack.dsm.common.*;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.common.vo.SimpleTreeDataVO;
import com.jkstack.dsm.user.controller.vo.*;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import com.jkstack.dsm.user.entity.UserEntity;
import com.jkstack.dsm.user.service.DepartmentService;
import com.jkstack.dsm.user.service.LRTreeService;
import com.jkstack.dsm.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * @author lifang
 * @since 2020/10/21
 */
@Api(tags = "部门管理")
@RestController()
@RequestMapping("/department")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private LRTreeService lrTreeService;

    @ApiOperation("更新部门LR算法")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功"))
    @PostMapping("/updateAllDeptLR")
    public ResponseResult updateAllDeptLR(){
        lrTreeService.updateAllNodeLR(DepartmentEntity.class);
        return ResponseResult.success();
    }

    @ApiOperation(value = "部门树列表")
    @GetMapping("/tree/list")
    public ResponseResult<List<DepartmentTreeVO>> list(@RequestParam(required = false) String departmentId){
        List<DepartmentTreeVO> departmentTreeList = null;
        if(StringUtils.isBlank(departmentId)){
            //未指定部门ID，默认加载deep<=3的部门树
            departmentTreeList = departmentService.listDepartmentTreeByDeepLE(1);
        }else{
            //指定部门ID，加载下级部门
            List<DepartmentEntity> departmentEntities = departmentService.listByParentDepartmentId(departmentId);
            departmentTreeList = departmentEntities.stream()
                    .map(DepartmentTreeVO::new)
                    .collect(Collectors.toList());
        }
        return ResponseResult.success(departmentTreeList);
    }

    @ApiOperation(value = "部门拖拽排序")
    @PostMapping("/tree/sort")
    public ResponseResult sort(@RequestBody DepartmentDropSortVO departmentDropSortVO) throws MessageException {
        //先校验参数是否正确
        checkDepartmentDropSortVO(departmentDropSortVO);

        List<DepartmentEntity> allDepartmentEntities = departmentService.listSiblingDepartmentByDepartmentId(departmentDropSortVO.getDropNode().getId());

        //重新生成sort,并且扩大10倍
        AtomicInteger atomicInteger = new AtomicInteger(1);
        allDepartmentEntities = allDepartmentEntities.stream()
                .peek(entity -> entity.setSort(atomicInteger.getAndIncrement() * 10))
                .sorted(Comparator.comparing(DepartmentEntity::getSort))
                .collect(Collectors.toList());

        DepartmentEntity dropDepartmentEntity = allDepartmentEntities.stream()
                .filter(entity -> StringUtils.equals(entity.getDepartmentId(), departmentDropSortVO.getDropNode().getId()))
                .findFirst()
                .orElse(null);

        Assert.isNull(dropDepartmentEntity, "拖拽的节点不存在");

        DepartmentEntity targetDepartmentEntity = allDepartmentEntities.stream()
                .filter(entity -> StringUtils.equals(entity.getDepartmentId(), departmentDropSortVO.getTargetNode().getId()))
                .findFirst()
                .orElse(null);

        Assert.isNull(targetDepartmentEntity, "拖拽的目标节点不存在");

        //重新排序。
        int sort = departmentDropSortVO.getDropType() == DepartmentDropSortVO.DropType.after ? targetDepartmentEntity.getSort() + 1 : targetDepartmentEntity.getSort() - 1;
        dropDepartmentEntity.setSort(sort);
        allDepartmentEntities = allDepartmentEntities.stream()
                .sorted(Comparator.comparing(DepartmentEntity::getSort))
                .collect(Collectors.toList());

        departmentService.updateDepartmentSort(allDepartmentEntities);
        return ResponseResult.success();
    }


    @ApiOperation(value = "部门明细信息")
    @GetMapping("/get")
    public ResponseResult<DepartmentVO> get(@RequestParam String departmentId) throws MessageException {

        DepartmentEntity departmentEntity = departmentService.getByBusinessId(departmentId);

        Assert.isNull(departmentEntity, "部门不存在");

        DepartmentVO departmentVO = new DepartmentVO(departmentEntity);

        DepartmentEntity parentDepartmentEntity = departmentService.getByBusinessId(departmentEntity.getParentDepartmentId());
        if(parentDepartmentEntity != null){
            departmentVO.setParentDepartmentId(parentDepartmentEntity.getDepartmentId());
            departmentVO.setOriginalParentDepartmentId(parentDepartmentEntity.getDepartmentId());
            departmentVO.setParentDepartmentName(parentDepartmentEntity.getName());
            departmentVO.setParentFullPathName(parentDepartmentEntity.getFullPathName());
        }

        UserEntity userEntity = userService.getByBusinessId(departmentEntity.getLeaderUserId());
        if(userEntity != null){
            departmentVO.setLeaderUserId(userEntity.getUserId());
            departmentVO.setLeaderUserName(userEntity.getName());
        }
        return ResponseResult.success(departmentVO);
    }

    @ApiOperation(value = "按部门名称模糊查询")
    @GetMapping("/name/query")
    public ResponseResult<List<DepartmentVO>> queryNameLike(@RequestParam String name) throws MessageException {
        List<DepartmentEntity> departmentEntities = departmentService.listByNameLike(name);
        List<DepartmentVO> result = departmentEntities.stream()
                .map(DepartmentVO::new)
                .collect(Collectors.toList());
        return ResponseResult.success(result);
    }


    @ApiOperation(value = "新建/编辑部门")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody @Valid DepartmentVO departmentVO) throws MessageException {
        checkDepartment(departmentVO);

        DepartmentEntity departmentEntity;
        if(StringUtils.isBlank(departmentVO.getDepartmentId())){
            //新建
            departmentEntity = new DepartmentEntity();

            //获取相同深度的节点最大排序值
            int sort = departmentService.getSameDeepMaxSortValueByParentDepartmentId(departmentVO.getParentDepartmentId());
            departmentEntity.setSort(sort + 10);
        }else{
            //更新
            departmentEntity = departmentService.getByBusinessId(departmentVO.getDepartmentId());
            Assert.isNull(departmentEntity, "部门不存在");
        }

        departmentEntity.setName(departmentVO.getName());
        departmentEntity.setParentDepartmentId(departmentVO.getParentDepartmentId());
        departmentEntity.setLeaderUserId(departmentVO.getLeaderUserId());
        departmentService.saveOrUpdate(departmentEntity);

        //更新LR算法。
        lrTreeService.updateAllNodeLR(DepartmentEntity.class);
        return ResponseResult.success();
    }


    @ApiOperation(value = "获取子部门列表")
    @GetMapping("/children/list")
    public ResponseResult<List<DepartmentChildrenListVO>> listChildren(@RequestParam String departmentId){
        List<DepartmentEntity> departmentEntities = departmentService.listByParentDepartmentId(departmentId);
        Map<String, Long> listUserNumberMap = departmentService.queryDeptUserNumber();

        List<DepartmentChildrenListVO> departmentChildrenList = departmentEntities.stream()
                .map(departmentEntity -> new DepartmentChildrenListVO(departmentEntity.getDepartmentId(), departmentEntity.getName(), listUserNumberMap.getOrDefault(departmentEntity.getDepartmentId(), 0L)))
                .collect(Collectors.toList());
        return ResponseResult.success(departmentChildrenList);
    }

    @ApiOperation("部门直属用户列表")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功"))
    @PostMapping("/users")
    public ResponseResult<PageResult<DepartmentUserVO>> listDepartmentUsers(@RequestBody PageVO queryVO) {
        String departmentId = queryVO.getId();
        DepartmentEntity departmentEntity = departmentService.getByBusinessId(departmentId);

        IPage<UserEntity> pageUser = userService.listDepartmentUsers(departmentId, queryVO);
        long allUserCount = userService.countUserByDepartmentId(departmentId);
        long deptCount = departmentService.countDepartmentByByDepartmentId(departmentId);

        List<DepartmentUserVO> departmentUserVOList = pageUser.getRecords().stream()
                .map(DepartmentUserVO::new)
                .collect(Collectors.toList());
        PageResult<DepartmentUserVO> pageResult = new PageResult(pageUser.getPages(), queryVO.getPageSize(), departmentUserVOList);

        //人数（全部子部门人数）
        pageResult.getExpand().put("allUserCount", allUserCount);
        //子部门数量(直属)
        pageResult.getExpand().put("deptCount", deptCount);
        pageResult.getExpand().put("name", departmentEntity.getName());
        return ResponseResult.success(pageResult);
    }

    @ApiOperation("删除部门")
    @GetMapping("/delete")
    public ResponseResult delete(@RequestParam String departmentId) throws MessageException {
        departmentService.deleteByDepartmentId(departmentId);
        lrTreeService.updateAllNodeLR(DepartmentEntity.class);
        return ResponseResult.success();
    }

    @ApiOperation("移除部门下用户")
    @PostMapping("/remove/user")
    public ResponseResult removeUser(@RequestBody @Valid DepartmentAddUserVO departmentAddUserVO) throws MessageException {
        String departmentId = departmentAddUserVO.getDepartmentId();
        Set<String> userIds = departmentAddUserVO.getUserIds();

        Assert.isEmpty(userIds, "请选择需要移除的用户！");

        departmentService.removeUser(departmentId, userIds);
        return ResponseResult.success();
    }


    @ApiOperation("部门添加成员列表")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功"))
    @PostMapping("/pick/user")
    public ResponseResult<PageResult<UserSimpleVO>> listUser(@RequestBody PageVO pageVO) {
        IPage<UserEntity> page = userService.pageByNotDepartmentId(pageVO.getId(), pageVO);

        List<UserSimpleVO> list = page.getRecords().stream()
                .map(UserSimpleVO::new)
                .collect(Collectors.toList());
        PageResult<UserSimpleVO> pageResult = new PageResult(page.getPages(), page.getTotal(), list);
        return ResponseResult.success(pageResult);
    }


    @ApiOperation("部门人添加用户")
    @PostMapping("/add/user")
    public ResponseResult addUser(@RequestBody @Valid DepartmentAddUserVO departmentAddUserVO) throws MessageException {

        departmentService.addUser(departmentAddUserVO.getDepartmentId(), departmentAddUserVO.getUserIds());

        return ResponseResult.success();
    }


    @ApiOperation("部门下成员列表")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功"))
    @GetMapping("/members")
    public ResponseResult<List<UserSimpleVO>> listUser(@RequestParam String departmentId) {
        return ResponseResult.success(userService.listAllByDepartmentId(departmentId));
    }

    private void checkDepartment(DepartmentVO departmentVO) throws MessageException {
        int deep = 1;
        if(StringUtils.isNotBlank(departmentVO.getParentDepartmentId())){
            DepartmentEntity parentDepartmentEntity = departmentService.getByBusinessId(departmentVO.getParentDepartmentId());
            Assert.isNull(parentDepartmentEntity, "未知的上级部门");
            deep = Optional.ofNullable(parentDepartmentEntity.getDeep()).orElse(0) + 1;
        }

        //同一层级，部门名称不允许重名
        departmentService.checkName(deep, departmentVO.getDepartmentId(), departmentVO.getName());

        if(StringUtils.isNotBlank(departmentVO.getLeaderUserId())){
            UserEntity userEntity = userService.getByBusinessId(departmentVO.getLeaderUserId());
            Assert.isNull(userEntity, "未知的部门主管");
            List<DepartmentEntity> departmentEntities = departmentService.listByUserId(userEntity.getUserId());
            boolean flag = departmentEntities.stream().anyMatch(departmentEntity -> StringUtils.equals(departmentEntity.getDepartmentId(), departmentVO.getDepartmentId()));
            if(!flag){
                throw new MessageException("部门主管设置错误，部门主管必须是当前部门的直属人员!");
            }
        }
    }

    private void checkDepartmentDropSortVO(DepartmentDropSortVO departmentDropSortVO) throws MessageException {
        SimpleTreeDataVO dropNode = departmentDropSortVO.getDropNode();
        SimpleTreeDataVO targetNode = departmentDropSortVO.getTargetNode();
        DepartmentDropSortVO.DropType dropType = departmentDropSortVO.getDropType();

        if(dropNode == null || StringUtils.isBlank(dropNode.getId())){
            throw new MessageException("拖拽节点参数错误");
        }

        if(targetNode == null || StringUtils.isBlank(targetNode.getId())){
            throw new MessageException("拖拽的目标节点参数错误");
        }

        Assert.isNull(dropType, "参数错误");
    }
}
