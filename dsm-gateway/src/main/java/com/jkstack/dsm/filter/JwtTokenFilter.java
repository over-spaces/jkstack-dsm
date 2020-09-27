package com.jkstack.dsm.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkstack.dsm.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class JwtTokenFilter implements GlobalFilter, Ordered {

    private static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);


    private ObjectMapper objectMapper;

    @Autowired
    private AntPathMatcher antPathMatcher; //路径匹配器

    public JwtTokenFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();

        //跳过不需要验证的url


        //获取token
        String token = exchange.getRequest().getHeaders().getFirst(JWTConstants.JWT_HEADER_KEY);
        ServerHttpResponse response = exchange.getResponse();

        if (StringUtils.isBlank(token)){
            //没有token
            return authError(response, "请登录");
        } else {
            try {
                //解析token
                Claims claims = JWTUtils.parseClaims(token);

                /*
                    根据具体业务
                    用户信息&权限验证
                */
                //claims.getBody()获取载荷
                //JWTUtils.getInfoFromToken()获取token中的用户信息

                if (claims.isEmpty()){ //签名验证通过
                    return chain.filter(exchange);
                }else {
                    return authError(response, "认证无效");
                }
            } catch (Exception e) {
                logger.error("检查token时异常: " + e);
                if (e.getMessage().contains("JWT expired"))
                    return authError(response, "认证过期");
                else
                    return authError(response, "认证失败");
            }
        }
    }

    /**
     * 认证错误输出
     * @param response 响应对象
     * @param msg 错误信息
     * @return 响应信息
     */
    private Mono<Void> authError(ServerHttpResponse response, String msg) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        //Dto returnFail = DtoUtil.returnFail(msg, HttpStatus.UNAUTHORIZED.toString());
        //String returnStr = "";
        //try {
        //    returnStr = objectMapper.writeValueAsString(returnFail);
        //} catch (JsonProcessingException e) {
        //    e.printStackTrace();
        //}
        //DataBuffer buffer = response.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        //return response.writeWith(Flux.just(buffer));
        return null;
    }


    @Override
    public int getOrder() {
        return -999;
    }
}
