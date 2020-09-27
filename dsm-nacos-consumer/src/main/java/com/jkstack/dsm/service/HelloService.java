package com.jkstack.dsm.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "nacos-provider")
public interface HelloService {


    @GetMapping("/hi")
    String hi(@RequestParam(name = "name") String name);

}
