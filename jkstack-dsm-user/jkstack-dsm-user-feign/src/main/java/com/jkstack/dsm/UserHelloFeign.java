package com.jkstack.dsm;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "dsm-user")
public interface UserHelloFeign {

    @GetMapping("/user/hello")
    ResponseResult hello();

}
