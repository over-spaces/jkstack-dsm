package com.jkstack.dsm.controller;

import com.jkstack.dsm.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/login")
    public ResponseResult login(){
        return new ResponseResult();
    }

    @GetMapping(value = "/hello")
    public ResponseResult hello(){
        return new ResponseResult("hello jkstack~~~");
    }
}
