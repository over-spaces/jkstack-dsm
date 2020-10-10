package com.jkstack.dsm.user.handler;

import com.jkstack.dsm.common.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lifang
 * @since 2020/10/10
 */
@RestControllerAdvice(basePackages = "com.jkstack.dsm.user")
public class HandlerException {

    private static Logger logger = LoggerFactory.getLogger(HandlerException.class);

    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<String> processException(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseResult.error("系统错误");
    }

}
