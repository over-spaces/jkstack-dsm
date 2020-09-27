package com.jkstack.dsm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hi")
    public String hi(@RequestParam(name = "name", defaultValue = "jack") String name){
        return "hi " + name;
    }

}
