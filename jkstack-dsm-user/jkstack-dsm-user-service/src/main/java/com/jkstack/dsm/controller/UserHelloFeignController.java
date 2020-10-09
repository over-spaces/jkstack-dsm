package com.jkstack.dsm.controller;

import com.jkstack.dsm.UserHelloFeign;
import com.jkstack.dsm.ResponseResult;
import com.jkstack.dsm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserHelloFeignController extends BaseController implements UserHelloFeign {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult hello(){
        return new ResponseResult("hello jkstack~~~");
    }

    @GetMapping(value = "/login")
    public ResponseResult login(){
        return new ResponseResult();
    }

    @GetMapping(value = "/user")
    public ResponseResult user(){
        return new ResponseResult(userService.findById(1L));
    }

}
