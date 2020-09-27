package com.jkstack.dsm.controller;

import com.jkstack.dsm.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${server.port}")
    String port;

    @Autowired
    HelloService helloService;

    @GetMapping("/hi")
    public String hi(@RequestParam(required = false) String name){
        return helloService.hi(name);
    }

    @GetMapping("/foo")
    public String foo(){
        return "hello world~~~, port:" + port;
    }

}
