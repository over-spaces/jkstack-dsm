package com.jkstack.dsm.common;

import java.io.Serializable;
import java.util.Collection;

public class ResponseResult<T> implements Serializable {

    private int code = ResponseResultCode.SUCCESS.getCode();

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

    public static <T> ResponseResult success(T data) {
        return new ResponseResult(data);
    }

    public static <T> ResponseResult<PageResult> success(long pageSize, long total, Collection<T> records) {
        long page = total / pageSize + 1;
        return new ResponseResult<>(new PageResult(page, total, records));
    }

    public static ResponseResult error(String message) {
        return error(ResponseResultCode.INTERNAL_SERVER_ERROR.getCode(), message);
    }

    public static ResponseResult error(int code, String message) {
        ResponseResult result = new ResponseResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public ResponseResult setCode(ResponseResultCode resultCode) {
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

    public String getResultCodeMsg() {
        return ResponseResultCode.get(code).getMsg();
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
