package com.jkstack.dsm.user;

import com.jkstack.dsm.common.MessageException;
import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.common.ResponseResultCodeEnum;
import com.jkstack.dsm.common.ServiceException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 统一异常处理类
 * @author lifang
 * @since 2020/10/10
 */
@RestControllerAdvice(basePackages = "com.jkstack.dsm.user")
public class HandlerException {

    private static Logger logger = LoggerFactory.getLogger(HandlerException.class);

    @ExceptionHandler({ FeignException.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<String> processBizException(FeignException e) {
        logger.error(e.getMessage(), e);
        return ResponseResult.error("系统错误");
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<String> processException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        return ResponseResult.error(ResponseResultCodeEnum.PARAMETER_ERROR.getCode(), msg);
    }

    @ExceptionHandler({ MessageException.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<String> processException(MessageException e) {
        return ResponseResult.error(e.getMessage());
    }

    @ExceptionHandler({ MissingServletRequestParameterException.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<String> processException(MissingServletRequestParameterException e) {
        return ResponseResult.error(ResponseResultCodeEnum.PARAMETER_ERROR, "接口参数错误!");
    }

    @ExceptionHandler({ ServiceException.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<String> processException(ServiceException e) {
        return ResponseResult.error(e.getMessage());
    }

    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<String> processException(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseResult.error("系统错误");
    }

}
