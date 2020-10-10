package com.jkstack.dsm;

import com.jkstack.dsm.common.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "dsm-user")
public interface UserHelloFeign {

    @GetMapping("/user/hello")
    ResponseResult hello();


    @GetMapping(value = "/user/say", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String say();

}
