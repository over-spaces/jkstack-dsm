package com.jkstack.dsm.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author lifang
 * @since 2020/11/6
 */
@FeignClient("dsm-admin")
public interface GatewayLoggerFeign {

    @PostMapping("/gateway/logger/save")
    void saveLog();

}
