package com.jkstack.dsm.filter;

import com.jkstack.dsm.utils.JWTUtils;
import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;

public class T {

    public static void main(String[] args) {
        Map<String, Object> param = new HashMap<>();
        param.put(JWTConstant.JWT_KEY_ID, "10001");
        param.put(JWTConstant.JWT_KEY_USER_NAME, "lisi");
        param.put(JWTConstant.JWT_KEY_ROLE, "admin");
        String token = JWTUtils.createJWT(param, "abc");

        Claims claims = JWTUtils.parseClaims(token);
        System.out.println(token);
    }
}
