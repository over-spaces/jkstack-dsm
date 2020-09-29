package com.jkstack.dsm.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkstack.dsm.ResponseResult;
import com.jkstack.dsm.ResponseResultCode;
import com.jkstack.dsm.redis.RedisCommand;
import com.jkstack.dsm.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenFilter implements GlobalFilter, Ordered {

    private static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    private RedisCommand redisCommand;

    private ObjectMapper objectMapper;

    private AntPathMatcher antPathMatcher; //路径匹配器


    public JwtTokenFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.antPathMatcher = new AntPathMatcher("/dsm-user/login");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();

        //跳过不需要验证的url
        if(antPathMatcher.isPattern(url)){
            return chain.filter(exchange);
        }

        //获取token
        String token = exchange.getRequest().getHeaders().getFirst(JWTConstant.JWT_HEADER_KEY);
        ServerHttpResponse response = exchange.getResponse();

        if (StringUtils.isBlank(token)){
            //没有token
            return authError(response, "请登录");
        } else {
            try {
                //解析token
                Claims claims = JWTUtils.parseClaims(token);
                if (Objects.nonNull(claims)){ //签名验证通过

                    final String key = JWTConstant.JWT_KEY_TTL.concat(claims.get(JWTConstant.JWT_KEY_ID).toString());

                    if (redisCommand.exists(key)) {
                        //更新token时间，使token不过期
                        redisCommand.set(key, token, 1, TimeUnit.HOURS);
                    }else{
                        return authError(response, "认证过期");
                    }
                    return chain.filter(exchange);
                }else {
                    return authError(response, "认证无效");
                }
            } catch (Exception e) {
                logger.error("检查token时异常: " + e);
                if (e.getMessage().contains("JWT expired")) {
                    return authError(response, "认证过期");
                } else {
                    return authError(response, "认证失败");
                }
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
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String result = "";
        try {
            result = objectMapper.writeValueAsString(new ResponseResult(ResponseResultCode.UNAUTHORIZED.getCode(), msg));
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        DataBuffer buffer = response.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));
    }


    @Override
    public int getOrder() {
        return -999;
    }
}
