package com.jkstack.dsm.common.utils;

public class JwtConstants {

    public static final String JWT_HEADER_KEY = "Authorization";

    public static final String JWT_KEY_SALT = "5e7fa853-506b-4171-8ba4-f136267964f3";

    public static final String JWT_KEY_USER_ID = "user_id";

    public static final String JWT_KEY_USER_NAME = "user_name";

    public static final String JWT_KEY_USER_ROLE = "user_role";

    public static final String JWT_KEY_TTL = "jwt_token_user_";

    //忽略权限认证的URL
    public static final String[] JWT_IGNORE_AUTH_URL = {"login", "/v3/api-docs"};
}