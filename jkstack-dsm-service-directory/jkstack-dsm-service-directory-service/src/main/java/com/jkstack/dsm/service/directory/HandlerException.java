package com.jkstack.dsm.service.directory;

import com.jkstack.dsm.common.MessageException;
import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.common.ServiceException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类
 * @author lifang
 * @since 2020/10/10
 */
@RestControllerAdvice(basePackages = "com.jkstack.dsm")
public class HandlerException {

    private static Logger logger = LoggerFactory.getLogger(HandlerException.class);

    @ExceptionHandler({ ServiceException.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<String> processException(ServiceException e) {
        logger.error(e.getMessage(), e);
        return ResponseResult.error(e.getMessage());
    }

    @ExceptionHandler({ MessageException.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<String> processException(MessageException e) {
        logger.error(e.getMessage(), e);
        return ResponseResult.error(e.getMessage());
    }


    @ExceptionHandler({ FeignException.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<String> processBizException(FeignException e) {
        logger.error(e.getMessage(), e);
        return ResponseResult.error("系统错误");
    }

    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<String> processException(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseResult.error("系统错误");
    }

}
