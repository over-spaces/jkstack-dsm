package com.jkstack.dsm.controller;

import com.jkstack.dsm.ResponseResult;
import com.jkstack.dsm.UserHelloFeign;
import com.jkstack.dsm.WeixinHelloFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeixinHelloController implements WeixinHelloFeign {

    @Value("${server.port}")
    public String port;

    @Autowired
    private UserHelloFeign userHelloFeign;

    @Override
    public ResponseResult hello() {
        return userHelloFeign.hello();
    }
}
