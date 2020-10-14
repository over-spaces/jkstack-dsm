package com.jkstack.dsm.common.utils;


import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public final class JwtUtils {

    private static ThreadLocal<JWTResult> currentJWTResult = new ThreadLocal();
    private static ThreadLocal<String> currentJWT = new ThreadLocal();

    private JwtUtils() {
    }

    public static String getAuthorizationFromParameter(String contentType ,String param){
        String result = null;
        if(StringUtils.isNotBlank(contentType)){
            if(!contentType.toLowerCase().contains("application/json")){
                result = param;
            }
        }else{
            result = param;
        }
        return result;
    }

    public static String createJWT(Map<String, Object> claims, String key, long validTime) {
        if (claims == null) {
            claims = new HashMap();
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setHeaderParam("typ", "JWT")
                .setIssuedAt(Date.from(zonedDateTime.toInstant()))
                .signWith(signatureAlgorithm, key.getBytes());

        if (validTime > 0L) {
            jwtBuilder.setExpiration(Date.from(dateTime.plusSeconds(validTime).atZone(ZoneId.systemDefault()).toInstant()));
        }
        return jwtBuilder.compact();
    }

    public static String createJWT(Map<String, Object> claims, String key) {
        return createJWT(claims, key, 0L);
    }

    public static JwtUtils.JWTResult parse(String jwtToken, String key) {
        JwtUtils.JWTResult jwtResult = null;

        try {
            jwtResult = new JwtUtils.JWTResult(Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(jwtToken));
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException | ExpiredJwtException var4) {
            jwtResult = new JwtUtils.JWTResult(var4);
        }

        return jwtResult;
    }

    public static Claims parseClaims(String jwtToken) {
        String claim = jwtToken.split("\\.")[1];
        String claimed = new String(Base64.getDecoder().decode(claim));
        try {
            return new DefaultClaims(JsonUtils.parseObject(claimed, Map.class));
        } catch (Exception var5) {
            return null;
        }
    }

    public static void addJWTResut2LocalCache(JwtUtils.JWTResult jwtResult) {
        currentJWTResult.set(jwtResult);
    }

    public static void addJWT2LocalCache(String jwt) {
        currentJWT.set(jwt);
    }

    public static void removeLocalCache() {
        currentJWTResult.remove();
        currentJWT.remove();
    }

    public static JwtUtils.JWTResult getCurrentJWTResult() {
        return (JwtUtils.JWTResult)currentJWTResult.get();
    }

    public static String getCurrentJWT() {
        return (String)currentJWT.get();
    }

    public static void clearJWTResultLocalCache() {
        currentJWTResult.remove();
    }

    private static String signKey(String key) {
        return ShaUtils.dSha1(key);
    }

    public static class JWTResult {
        private Jws<Claims> origin;
        private boolean isValid = false;
        private Exception ex;
        private String msg = "";

        public JWTResult(Jws<Claims> claims) {
            this.origin = claims;
            this.isValid = true;
        }

        public JWTResult(Exception ex) {
            this.ex = ex;
            this.msg = ex.getMessage();
        }

        public Optional<Object> get(String key) {
            Optional<Object> result = Optional.empty();
            if (this.origin != null) {
                result = Optional.of(((Claims)this.origin.getBody()).get(key));
            }

            return result;
        }

        public <T> Optional<T> get(String key, Class<T> requiredType) {
            Optional<T> result = Optional.empty();
            if (this.origin != null) {
                result = Optional.of(((Claims)this.origin.getBody()).get(key, requiredType));
            }

            return result;
        }

        public Map<String, Object> asMap() {
            return (Map)(this.origin != null ? (Map)this.origin.getBody() : new HashMap());
        }

        public boolean isValid() {
            return this.isValid;
        }

        public Exception getEx() {
            return this.ex;
        }

        public String getMsg() {
            return this.msg;
        }

        public Jws<Claims> getOrigin() {
            return this.origin;
        }
    }


}
