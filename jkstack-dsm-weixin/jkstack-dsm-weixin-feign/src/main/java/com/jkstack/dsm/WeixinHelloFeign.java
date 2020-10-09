package com.jkstack.dsm;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "dsm-weixin")
public interface WeixinHelloFeign {

    @GetMapping("/weixin/hello")
    ResponseResult hello();

}
