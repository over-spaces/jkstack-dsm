package com.jkstack.dsm.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jkstack.dsm.common.*;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.user.controller.vo.WorkGroupAddUserVO;
import com.jkstack.dsm.user.controller.vo.WorkGroupUserVO;
import com.jkstack.dsm.user.controller.vo.WorkGroupVO;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import com.jkstack.dsm.user.entity.UserEntity;
import com.jkstack.dsm.user.entity.WorkGroupEntity;
import com.jkstack.dsm.user.service.DepartmentService;
import com.jkstack.dsm.user.service.UserService;
import com.jkstack.dsm.user.service.WorkGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (WorkGroup)表控制层
 *
 * @author lifang
 * @since 2020-10-27 11:48:42
 */
@Api(tags = "工作组")
@RestController
@RequestMapping("/work-group")
public class WorkGroupController extends BaseController {

    @Autowired
    private WorkGroupService workGroupService;
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;


    @ApiOperation(value = "新建/修改工作组")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody @Valid WorkGroupVO workGroupVO) throws MessageException {
        checkWorkGroupVO(workGroupVO);
        WorkGroupEntity workGroupEntity;
        if (StringUtils.isBlank(workGroupVO.getWorkGroupId())) {
            workGroupEntity = new WorkGroupEntity();
            int sort = workGroupService.getMaxSortValue();
            workGroupEntity.setSort(sort + 1);
        } else {
            workGroupEntity = workGroupService.getByBusinessId(workGroupVO.getWorkGroupId());
            Assert.isNull(workGroupEntity, "工作组不存在");
        }
        workGroupEntity.setName(workGroupVO.getName());
        workGroupEntity.setDescription(workGroupVO.getDescription());
        workGroupService.saveOrUpdate(workGroupEntity);
        return ResponseResult.success();
    }


    @ApiOperation(value = "获取工作组明细")
    @GetMapping("/get")
    public ResponseResult<WorkGroupVO> get(@RequestParam String workGroupId) throws MessageException {
        WorkGroupEntity workGroupEntity = workGroupService.getByBusinessId(workGroupId);
        Assert.isNull(workGroupEntity, "工作组不存在");

        //用户数
        Long userCount = workGroupService.selectCountUserByWorkGroupId(workGroupEntity.getWorkGroupId());

        WorkGroupVO workGroupVO = new WorkGroupVO(workGroupEntity);
        workGroupVO.setUserCount(userCount);
        return ResponseResult.success(workGroupVO);
    }

    @ApiOperation(value = "删除工作组")
    @GetMapping("/delete")
    public ResponseResult<WorkGroupVO> delete(@RequestParam String workGroupId) throws MessageException {

        workGroupService.deleteByWorkGroupId(workGroupId);

        return ResponseResult.success();
    }

    @ApiOperation(value = "工作组列表")
    @GetMapping("/list")
    public ResponseResult<List<WorkGroupVO>> list() throws MessageException {
        List<WorkGroupEntity> workGroupEntities = workGroupService.list();
        List<WorkGroupVO> list = workGroupEntities.stream()
                .sorted(Comparator.comparing(WorkGroupEntity::getSort))
                .map(WorkGroupVO::new)
                .collect(Collectors.toList());
        return ResponseResult.success(list);
    }

    @ApiOperation(value = "工作组成员列表")
    @PostMapping("/users")
    public ResponseResult<PageResult<WorkGroupUserVO>> users(@RequestBody PageVO pageVO) throws MessageException {
        WorkGroupEntity workGroupEntity = workGroupService.getByBusinessId(pageVO.getId());

        Assert.isNull(workGroupEntity, "工作组不存在");

        //用户数
        Long userCount = workGroupService.selectCountUserByWorkGroupId(workGroupEntity.getWorkGroupId());

        IPage<UserEntity> page = userService.listByWorkGroupId(pageVO.getId(), pageVO);
        List<String> userIds = page.getRecords().stream().map(UserEntity::getUserId).collect(Collectors.toList());

        //部门
        Map<String, List<DepartmentEntity>> userDepartmentMap = departmentService.listByUsers(userIds);

        List<WorkGroupUserVO> list = page.getRecords().stream()
                .map(userEntity -> new WorkGroupUserVO(userEntity, userDepartmentMap.get(userEntity.getUserId())))
                .collect(Collectors.toList());
        PageResult<WorkGroupUserVO> pageResult = new PageResult(page, list);
        pageResult.getExpand().put("workGroupId", workGroupEntity.getWorkGroupId());
        pageResult.getExpand().put("name", workGroupEntity.getName());
        pageResult.getExpand().put("userCount", userCount);
        return ResponseResult.success(pageResult);
    }

    @ApiOperation(value = "工作组添加成员")
    @PostMapping("/add/user")
    public ResponseResult addUser(@RequestBody WorkGroupAddUserVO workGroupAddUserVO) throws MessageException {

        Assert.isEmpty(workGroupAddUserVO.getUserIds(), "请选择需要添加的成员");

        workGroupService.addUser(workGroupAddUserVO.getWorkGroupId(), workGroupAddUserVO.getUserIds());

        return ResponseResult.success();
    }

    @ApiOperation(value = "工作组移除成员")
    @PostMapping("/remove/user")
    public ResponseResult removeUser(@RequestBody WorkGroupAddUserVO workGroupAddUserVO) throws MessageException {
        Assert.isEmpty(workGroupAddUserVO.getUserIds(), "请选择需要移除的成员");

        workGroupService.removeUser(workGroupAddUserVO.getWorkGroupId(), workGroupAddUserVO.getUserIds());

        return ResponseResult.success();
    }

    @ApiOperation(value = "工作组名称校验")
    @GetMapping("/check/name")
    public ResponseResult checkName(@RequestParam(required = false) String workGroupId, @RequestParam String name) throws MessageException {
        WorkGroupEntity workGroupEntity = workGroupService.getByName(name);
        if(workGroupEntity == null){
            return ResponseResult.success();
        }
        if(!StringUtils.equals(workGroupId, workGroupEntity.getWorkGroupId())){
            throw new MessageException("工作组已存在");
        }
        return ResponseResult.success();
    }

    @ApiOperation(value = "工作组排序")
    @PostMapping("/sort")
    public ResponseResult sort(@RequestBody List<WorkGroupVO> workGroupList) {

        if(CollectionUtils.isEmpty(workGroupList)) {
            return ResponseResult.success();
        }

        List<String> workGroupIds = workGroupList.stream()
                .filter(workGroupVO -> StringUtils.isNotBlank(workGroupVO.getWorkGroupId()))
                .map(WorkGroupVO::getWorkGroupId)
                .distinct()
                .collect(Collectors.toList());

        workGroupService.updateSort(workGroupIds);

        return ResponseResult.success();
    }

    private void checkWorkGroupVO(@Valid WorkGroupVO workGroupVO) throws MessageException {
        checkName(workGroupVO.getWorkGroupId(), workGroupVO.getName());
    }
}