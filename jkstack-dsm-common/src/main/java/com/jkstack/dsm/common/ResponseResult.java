package com.jkstack.dsm.common;

import java.io.Serializable;
import java.util.Collection;

/**
 * @param <T>
 * @author lifang
 * @since 2020-10-01
 */
public class ResponseResult<T> implements Serializable {

    private int code = ResponseResultCodeEnum.SUCCESS.getCode();

    private String message;

    private T data;

    public ResponseResult() {
    }

    public ResponseResult(T data) {
        this.data = data;
    }


    public ResponseResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ResponseResult success() {
        return new ResponseResult<T>();
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>(data);
    }

    public static ResponseResult error(String message) {
        return error(ResponseResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), message);
    }

    public static ResponseResult error(ResponseResultCodeEnum codeEnum, String message) {
        ResponseResult result = new ResponseResult();
        result.setCode(codeEnum.getCode());
        result.setMessage(message);
        return result;
    }

    public static ResponseResult error(int code, String message) {
        ResponseResult result = new ResponseResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public ResponseResult setCode(ResponseResultCodeEnum resultCode) {
        this.code = resultCode.getCode();
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResponseResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseResult setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Result{");
        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
