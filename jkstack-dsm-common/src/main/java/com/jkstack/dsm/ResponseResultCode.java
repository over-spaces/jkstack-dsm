package com.jkstack.dsm;

import com.jkstack.dsm.utils.LambdaUtil;

import java.util.Arrays;
import java.util.Map;

public enum ResponseResultCode {

    SUCCESS(200, "成功"),//成功

    FAIL(400, "失败"),//失败

    UNAUTHORIZED(401),//未认证（签名错误）

    NOT_FOUND(404),//接口不存在

    NULL(0),//接口不存在

    INTERNAL_SERVER_ERROR(500);//服务器内部错误

    private int code;

    private String msg;

    ResponseResultCode(int code) {
        this.code = code;
    }

    ResponseResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseResultCode get(Integer value) {
        Map<Integer, ResponseResultCode> map = LambdaUtil.list2map(Arrays.asList(ResponseResultCode.values()), a -> a.code);
        ResponseResultCode resultCode = map.get(value);
        return resultCode != null ? resultCode : NULL;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
