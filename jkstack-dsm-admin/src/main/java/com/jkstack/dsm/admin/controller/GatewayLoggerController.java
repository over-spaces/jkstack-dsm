package com.jkstack.dsm.admin.controller;

import com.jkstack.dsm.common.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lifang
 * @since 2020/11/6
 */
@RestController
public class GatewayLoggerController{

    private static final Logger logger = LoggerFactory.getLogger(GatewayLoggerController.class);

    @PostMapping("/gateway/logger/save")
    public ResponseResult saveLogger() {
        logger.info("==================================================================================================");
        return ResponseResult.success();
    }
}
