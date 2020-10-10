package com.jkstack.dsm;

import com.jkstack.dsm.common.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "dsm-weixin")
public interface WeixinHelloFeign {

    @GetMapping("/weixin/hello")
    ResponseResult hello();

}
