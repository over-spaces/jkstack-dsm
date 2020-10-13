package com.jkstack.dsm;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 对其他模块暴露的User相关API， 通过feign模块间调用。
 * @author lifang
 * @since 2020/10/13
 */
@FeignClient(value = "dsm-user")
public interface UserControllerFeign {
}
