package com.jkstack.dsm.user.controller;

import com.jkstack.dsm.UserControllerFeign;
import com.jkstack.dsm.common.BaseController;
import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.common.redis.RedisCommand;
import com.jkstack.dsm.common.utils.JwtConstants;
import com.jkstack.dsm.common.utils.JwtUtils;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.user.entity.UserEntity;
import com.jkstack.dsm.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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


    @ApiOperation("创建用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = JwtConstants.JWT_HEADER_KEY, value = "Token", required = true, paramType = "header", dataType = "string")
    })
    @PostMapping("/create")
    public ResponseResult create(@RequestBody UserVO user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLoginName(user.getLoginName());
        userService.save(userEntity);
        return new ResponseResult();
    }

    @ApiOperation("更新用户")
    @PostMapping("/update")
    public ResponseResult update() {
        UserEntity userEntity = userService.findById(1L);
        userEntity.setUserId(UUID.randomUUID().toString());
        return new ResponseResult();
    }


    @ApiOperation("用户列表")
    @PostMapping("/list")
    public ResponseResult list(@RequestBody PageVO page, HttpServletRequest request) {
        return new ResponseResult(userService.listUsers(page.getPageNo(), page.getPageSize()));
    }

    @ApiOperation("移除用户")
    @PostMapping("/delete")
    public ResponseResult delete(@RequestBody List<Long> userIds) {
        return new ResponseResult();
    }

}
