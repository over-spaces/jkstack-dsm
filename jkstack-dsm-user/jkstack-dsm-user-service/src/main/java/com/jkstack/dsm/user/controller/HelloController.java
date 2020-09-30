package com.jkstack.dsm.user.controller;

import com.jkstack.dsm.ResponseResult;
import com.jkstack.dsm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    public ResponseResult login(){
        return new ResponseResult();
    }

    @GetMapping(value = "/hello")
    public ResponseResult hello(){
        return new ResponseResult("hello jkstack~~~");
    }

    @GetMapping(value = "/user")
    public ResponseResult user(){
        return new ResponseResult(userService.findById(1L));
    }

}
