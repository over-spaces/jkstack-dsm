package com.jkstack.dsm.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.common.ResponseResultCode;
import com.jkstack.dsm.common.redis.RedisCommand;
import com.jkstack.dsm.common.utils.JWTConstant;
import com.jkstack.dsm.common.utils.JWTUtils;
import com.jkstack.dsm.common.utils.StringUtil;
import io.jsonwebtoken.Claims;
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
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 权限认证，校验token是否合法。
 *
 * @author lifang
 * @since 2020-9-30
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private RedisCommand redisCommand;

    private ObjectMapper objectMapper;

    public AuthFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final String url = exchange.getRequest().getURI().getPath();

        //跳过不需要验证的url
        if (isIgnoreAuthURL(url)) {
            return chain.filter(exchange);
        }

        //获取token
        String token = exchange.getRequest().getHeaders().getFirst(JWTConstant.JWT_HEADER_KEY);
        ServerHttpResponse response = exchange.getResponse();

        if (StringUtils.isBlank(token)) {
            //没有token
            return authError(response, "请登录");
        } else {
            try {
                //解析token
                Claims claims = JWTUtils.parseClaims(token);
                if (Objects.nonNull(claims)) { //签名验证通过

                    final String key = JWTConstant.JWT_KEY_TTL.concat(claims.get(JWTConstant.JWT_KEY_ID).toString());

                    if (redisCommand.exists(key)) {
                        //更新token时间，使token不过期
                        redisCommand.set(key, token, 1, TimeUnit.HOURS);
                    } else {
                        return authError(response, "认证过期");
                    }
                    return chain.filter(exchange);
                } else {
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


    @Override
    public int getOrder() {
        //值越小，越先执行，权限认证优先级需要最高。
        return -100;
    }

    /**
     * 是否是忽略权限认证的URL
     */
    private boolean isIgnoreAuthURL(String url){
        return Arrays.stream(JWTConstant.JWT_IGNORE_AUTH_URL)
                .anyMatch(ignoreUrl -> StringUtil.containsIgnoreCase(url, ignoreUrl));
    }

    /**
     * 认证错误输出
     *
     * @param response 响应对象
     * @param msg      错误信息
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
}
