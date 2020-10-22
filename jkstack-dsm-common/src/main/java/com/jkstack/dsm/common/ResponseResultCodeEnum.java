package com.jkstack.dsm.common;

import java.util.Arrays;

public enum ResponseResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200),

    /**
     * 失败
     */
    FAIL(400),

    /**
     * 未认证（签名错误）
     */
    UNAUTHORIZED(401),

    /**
     * 接口不存在
     */
    NOT_FOUND(404),

    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500),

    /**
     * 参数错误
     */
    PARAMETER_ERROR(1000);

    private int code;

    ResponseResultCodeEnum(int code) {
        this.code = code;
    }

    public static ResponseResultCodeEnum get(Integer code) {
        return Arrays.stream(ResponseResultCodeEnum.values())
                .filter(e -> e.code == code)
                .findFirst()
                .orElse(INTERNAL_SERVER_ERROR);
    }

    public int getCode() {
        return code;
    }
}
