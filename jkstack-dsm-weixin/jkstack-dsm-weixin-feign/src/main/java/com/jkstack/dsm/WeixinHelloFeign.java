package com.jkstack.dsm;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "dsm-weixin")
public interface WeixinHelloFeign {


}
