package com.jkstack.dsm.controller;

import com.google.common.collect.Maps;
import com.jkstack.dsm.UserHelloFeign;
import com.jkstack.dsm.common.BaseController;
import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.common.utils.JWTConstant;
import com.jkstack.dsm.common.utils.JWTUtils;
import com.jkstack.dsm.controller.vo.LoginVO;
import com.jkstack.dsm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserHelloController extends BaseController implements UserHelloFeign {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult hello(){
        return new ResponseResult("hello jkstack~~~");
    }

    @Override
    public String say() {
        return "hello world!!!";
    }

    @GetMapping(value = "/login")
    public ResponseResult login(){
        LoginVO loginVO = new LoginVO();

        Map<String, Object> claims = Maps.newHashMap();
        claims.put(JWTConstant.JWT_KEY_ID, "10001");
        claims.put(JWTConstant.JWT_KEY_USER_NAME, "tom");
        claims.put(JWTConstant.JWT_KEY_ROLE, "admin");

        loginVO.setToken(JWTUtils.createJWT(claims, JWTConstant.JWT_KEY_SALT));
        return ResponseResult.SUCCESS(loginVO);
    }

    @GetMapping(value = "/user")
    public ResponseResult user(){
        return new ResponseResult(userService.findById(1L));
    }

}
