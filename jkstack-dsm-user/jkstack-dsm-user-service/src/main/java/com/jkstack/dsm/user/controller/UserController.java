package com.jkstack.dsm.user.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkstack.dsm.UserControllerFeign;
import com.jkstack.dsm.common.*;
import com.jkstack.dsm.common.redis.RedisCommand;
import com.jkstack.dsm.common.utils.ExcelUtil;
import com.jkstack.dsm.common.utils.JwtConstants;
import com.jkstack.dsm.common.utils.JwtUtils;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.common.vo.SimpleDataVO;
import com.jkstack.dsm.user.controller.vo.UserExcelVO;
import com.jkstack.dsm.user.controller.vo.UserSelectVO;
import com.jkstack.dsm.user.controller.vo.UserSimpleVO;
import com.jkstack.dsm.user.controller.vo.UserVO;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import com.jkstack.dsm.user.entity.UserEntity;
import com.jkstack.dsm.user.entity.WorkGroupEntity;
import com.jkstack.dsm.user.service.DepartmentService;
import com.jkstack.dsm.user.service.UserService;
import com.jkstack.dsm.user.service.WorkGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.compress.utils.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lifang
 * @since 2020/10/13
 */
@Api(tags = "用户管理")
@RestController()
@RequestMapping("/user")
public class UserController extends BaseController implements UserControllerFeign {

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private WorkGroupService workGroupService;
    @Autowired
    private RedisCommand redisCommand;

    @ApiOperation("登录")
    @PostMapping("/login")
    public ResponseResult login() {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstants.JWT_KEY_USER_ID, "10001");
        claims.put(JwtConstants.JWT_KEY_USER_NAME, "jack");
        claims.put(JwtConstants.JWT_KEY_USER_ROLE, "admin");
        String token = JwtUtils.createJWT(claims, JwtConstants.JWT_KEY_SALT);

        final String key = JwtConstants.JWT_KEY_TTL.concat(claims.get(JwtConstants.JWT_KEY_USER_ID).toString());
        redisCommand.set(key, token, 1, TimeUnit.HOURS);
        return new ResponseResult(token);
    }


    @ApiOperation("创建/编辑用户信息")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody @Valid UserVO userVO) throws MessageException {
        checkLoginName(userVO.getUserId(), userVO.getLoginName());

        UserEntity userEntity;
        if (StringUtils.isBlank(userVO.getUserId())) {
            //新建
            userEntity = userVO.toUserEntity(new UserEntity());
            userService.createUser(userEntity);
        } else {
            //编辑
            userEntity = userService.getByBusinessId(userVO.getUserId());
            Assert.isNull(userEntity,"用户不存在");
            userEntity = userVO.toUserEntity(userEntity);

            //用户所属部门
            List<SimpleDataVO> departmentList = userVO.getDepartmentList();
            Set<String> departmentIds = departmentList.stream().map(SimpleDataVO::getId).collect(Collectors.toSet());

            //用户所属工作组
            List<String> workGroupIds = userVO.getWorkGroupList();

            userService.update(userEntity, departmentIds, workGroupIds);
        }
        return new ResponseResult();
    }


    @ApiOperation(value = "获取单个用户信息明细")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功"))
    @GetMapping("/get")
    public ResponseResult<UserVO> get(@RequestParam String userId) throws MessageException {
        UserEntity userEntity = userService.getByBusinessId(userId);
        Assert.isNull(userEntity, "用户不存在");

        //部门
        List<DepartmentEntity> departmentEntities = departmentService.listByUserId(userEntity.getUserId());
        List<SimpleDataVO> departmentList = departmentEntities.stream()
                .sorted(Comparator.comparing(DepartmentEntity::getSort))
                .map(entity -> new SimpleDataVO(entity.getDepartmentId(), entity.getName())).collect(Collectors.toList());

        //工作组
        List<WorkGroupEntity> workGroupEntities = workGroupService.listByUserId(userEntity.getUserId());
        List<String> workGroupIds = workGroupEntities.stream().map(WorkGroupEntity::getWorkGroupId).collect(Collectors.toList());

        UserVO userVO = new UserVO(userEntity);
        userVO.setDepartmentList(departmentList);
        userVO.setWorkGroupList(workGroupIds);
        return new ResponseResult<>(userVO);
    }

    @ApiOperation("用户列表")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功"))
    @PostMapping("/list")
    public ResponseResult<PageResult<UserVO>> list(@RequestBody PageVO pageVO) {
        IPage<UserEntity> page = userService.pageByLike(pageVO);

        List<String> userIds = page.getRecords().stream().map(UserEntity::getUserId).collect(Collectors.toList());
        Map<String, List<WorkGroupEntity>> userWorkGroupMap = workGroupService.listByUserIds(userIds);
        Map<String, List<DepartmentEntity>> userDepartmentMap = departmentService.listByUsers(userIds);

        List<UserVO> list = page.getRecords().stream().map(UserVO::new)
                .peek(user -> {
                    //部门
                    List<SimpleDataVO> userDepartmentList = userDepartmentMap.getOrDefault(user.getUserId(), Collections.emptyList()).stream()
                            .map(departmentEntity -> new SimpleDataVO(departmentEntity.getDepartmentId(), departmentEntity.getFullPathName()))
                            .collect(Collectors.toList());
                    user.setDepartmentList(userDepartmentList);

                    //工作组
                    // List<SimpleDataVO> userWorkGroupList = userWorkGroupMap.getOrDefault(user.getUserId(), Collections.emptyList()).stream()
                    //         .map(workGroupEntity -> new SimpleDataVO(workGroupEntity.getWorkGroupId(), workGroupEntity.getName()))
                    //         .collect(Collectors.toList());
                    // user.setWorkGroupList(userWorkGroupList);
                })
                .collect(Collectors.toList());

        PageResult<UserVO> pageResult = new PageResult(page.getCurrent(), page.getTotal(), list);
        return new ResponseResult<>(pageResult);
    }

    @ApiOperation("选择成员列表")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功"))
    @PostMapping("/select/members")
    public ResponseResult<PageResult<UserSimpleVO>> selectMembers(@RequestBody UserSelectVO selectVO){
        String departmentId = selectVO.getDepartmentId();

        IPage<UserSimpleVO> pageUser = null;
        if(selectVO.getType() == UserSelectVO.TypeEnum.WORK_GROUP){

            String workGroupId = selectVO.getId();
            pageUser = userService.pageByDepartmentIdAndNotWorkGroupId(departmentId, workGroupId, selectVO);


        }else if(selectVO.getType() == UserSelectVO.TypeEnum.PERM_GROUP){

            pageUser = new Page<>();

        }else {

            pageUser = userService.pageByDepartmentId(departmentId, selectVO);

        }
        PageResult<UserSimpleVO> pageResult = new PageResult(pageUser, pageUser.getRecords());
        return ResponseResult.success(pageResult);
    }

    @ApiOperation("移除用户")
    @PostMapping("/delete")
    public ResponseResult delete(@RequestBody List<String> userIds) throws MessageException {
        Assert.isNull(userIds, "请选择需要删除的用户列表");
        userService.deleteByUserId(userIds);
        return new ResponseResult<>();
    }

    @ApiOperation("下载Excel导入模板")
    @GetMapping("/template/download")
    public ResponseResult downloadTemplate(HttpServletResponse response) {
        Workbook workbook = ExcelUtil.exportExcel(new ExportParams("导入用户信息", "导入用户信息"), UserExcelVO.class, Lists.newArrayList());
        downloadExcel("导入用户信息.xls", workbook, response);
        return new ResponseResult<>();
    }

    @ApiOperation("导入用户")
    @PostMapping("/import")
    public ResponseResult<ImportResult> downloadTemplate(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setVerifyHandler(new UserExcelImportVerifyHandler(userService));
        ExcelImportResult<UserExcelVO> result = ExcelImportUtil.importExcelMore(file.getInputStream(), UserExcelVO.class, params);

        List<UserEntity> list = result.getList().stream()
                .map(UserExcelVO::toUserEntity)
                .collect(Collectors.toList());
        userService.createUser(list);
        ImportResult importResult = new ImportResult(result);
        return new ResponseResult<>(importResult);
    }

    @ApiOperation("重置用户密码")
    @GetMapping("/password/reset")
    public ResponseResult resetPassword(@RequestParam String userId) throws MessageException {
        UserEntity userEntity = getByBusinessId(userId);
        userEntity.setPassword(userService.getUserInitPassword());
        userService.updateById(userEntity);
        return ResponseResult.success();
    }

    @ApiOperation("校验用户名是否合法")
    @GetMapping("/check/login-name")
    public ResponseResult checkLoginName(@RequestParam(required = false) String userId,
                                         @RequestParam String loginName) throws MessageException {
        if (!userService.checkUserLoginName(userId, loginName)) {
            throw new MessageException("用户已存在!");
        }
        return ResponseResult.success();
    }

    private UserEntity getByBusinessId(String userId) throws MessageException {
        UserEntity userEntity = userService.getByBusinessId(userId);
        Assert.isNull(userEntity, "用户不存在");
        return userEntity;
    }
}
