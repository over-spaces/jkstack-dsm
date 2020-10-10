package com.jkstack.dms;

import com.jkstack.dsm.common.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class JWTUtilsTest {

    private static Logger logger = LoggerFactory.getLogger(JWTUtilsTest.class);

    private static final String JWT_KEY = "5e7fa853-506b-4171-8ba4-f136267964f3";

    @Test
    public void createJWT(){
        String token = JWTUtils.createJWT(claims(), JWT_KEY);
        logger.info("token: {}", token);
    }

    private Map<String, Object> claims(){
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", "10001");
        params.put("user_name", "jack");
        params.put("user_role", "admin,hr");
        return params;
    }
}
