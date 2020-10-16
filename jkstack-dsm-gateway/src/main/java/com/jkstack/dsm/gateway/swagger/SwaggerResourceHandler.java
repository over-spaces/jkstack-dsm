package com.jkstack.dsm.gateway.swagger;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author lifang
 * @since 2020/10/16
 */
public class SwaggerResourceHandler implements HandlerFunction<ServerResponse> {

    @Resource
    private GatewaySwaggerProvider swaggerProvider;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(swaggerProvider.get()));
    }
}
