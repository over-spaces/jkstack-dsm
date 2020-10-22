package com.jkstack.dsm.user.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelDataHandler;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import cn.afterturn.easypoi.handler.inter.IReadHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkstack.dsm.UserControllerFeign;
import com.jkstack.dsm.common.*;
import com.jkstack.dsm.common.redis.RedisCommand;
import com.jkstack.dsm.common.utils.JwtConstants;
import com.jkstack.dsm.common.utils.JwtUtils;
import com.jkstack.dsm.common.utils.MD5Util;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.user.config.UserConfigProperties;
import com.jkstack.dsm.user.controller.vo.UserExcelVO;
import com.jkstack.dsm.user.controller.vo.UserListVO;
import com.jkstack.dsm.user.controller.vo.UserVO;
import com.jkstack.dsm.user.entity.UserEntity;
import com.jkstack.dsm.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
            if (userEntity == null) {
                throw new MessageException("用户不存在!");
            }
            userService.updateById(userEntity);
        }
        return new ResponseResult();
    }


    @ApiOperation(value = "获取单个用户信息明细")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功"))
    @GetMapping("/get")
    public ResponseResult<UserVO> get(@RequestParam String userId) throws MessageException {
        UserEntity userEntity = userService.getByBusinessId(userId);

        if (Objects.isNull(userEntity)) {
            throw new MessageException("用户不存在!");
        }

        UserVO userVO = new UserVO(userEntity);
        return new ResponseResult<>(userVO);
    }

    @ApiOperation("用户列表")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功"))
    @PostMapping("/list")
    public ResponseResult<PageResult<UserListVO>> list(@RequestBody PageVO pageVO) {
        IPage<UserEntity> page;
        LambdaUpdateWrapper<UserEntity> wrapper = getLikeQueryWrapper(pageVO.getCondition());
        if(Objects.isNull(wrapper)){
            page = userService.page(new Page(pageVO.getPageNo(), pageVO.getPageSize()));
        }else {
            page = userService.page(new Page(pageVO.getPageNo(), pageVO.getPageSize()), wrapper);
        }

        List<UserListVO> list = page.getRecords().stream()
                .map(UserListVO::new)
                .collect(Collectors.toList());

        PageResult<UserListVO> pageResult = new PageResult(page.getCurrent(), page.getTotal(), list);
        return new ResponseResult<>(pageResult);
    }

    @ApiOperation("移除用户")
    @PostMapping("/delete")
    public ResponseResult delete(@RequestBody List<String> userIds) throws MessageException {
        if (CollectionUtils.isEmpty(userIds)) {
            throw new MessageException("请选择需要删除的用户列表!");
        }
        userService.deleteByUserId(userIds);
        return new ResponseResult<>();
    }

    @ApiOperation("下载Excel导入模板")
    @GetMapping("/template/download")
    public ResponseResult downloadTemplate(HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("导入用户信息", "导入用户信息"), UserExcelVO.class, Lists.newArrayList());
        downloadExcel("导入用户信息.xlsx", workbook, response);
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
            throw new MessageException("用户名不合法或已存在!");
        }
        return ResponseResult.success();
    }


    private LambdaUpdateWrapper<UserEntity> getLikeQueryWrapper(String condition) {
        if (StringUtils.isBlank(condition)) {
            return null;
        }
        LambdaUpdateWrapper<UserEntity> wrapper = new LambdaUpdateWrapper<>(UserEntity.class);
        wrapper.like(UserEntity::getLoginName, "%" + condition + "%")
                .or()
                .like(UserEntity::getName, "%" + condition + "%")
                .or()
                .like(UserEntity::getUserNo, "%" + condition + "%");
        return wrapper;
    }

    private UserEntity getByBusinessId(String userId) throws MessageException {
        UserEntity userEntity = userService.getByBusinessId(userId);
        if (Objects.isNull(userEntity)) {
            throw new MessageException("用户不存在!");
        }
        return userEntity;
    }
}
