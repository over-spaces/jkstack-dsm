package com.jkstack.dsm.weixin.controller;

import com.jkstack.dsm.WeixinHelloFeign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeixinHelloController implements WeixinHelloFeign {

    @Value("${server.port}")
    public String port;

}
