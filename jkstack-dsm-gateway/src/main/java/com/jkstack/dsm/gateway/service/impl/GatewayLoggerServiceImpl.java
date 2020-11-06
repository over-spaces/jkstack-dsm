package com.jkstack.dsm.gateway.service.impl;

import com.jkstack.dsm.gateway.service.GatewayLoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author lifang
 * @since 2020/11/6
 */
@Component
public class GatewayLoggerServiceImpl implements GatewayLoggerService {

    private static final Logger logger = LoggerFactory.getLogger(GatewayLoggerServiceImpl.class);


    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public void saveLog() {
        try {
            Mono<Void> mono = webClientBuilder.build().post().uri("http://dsm-admin/gateway/logger/save").retrieve().bodyToMono(Void.class);
            // 输出结果
            logger.debug(String.valueOf(mono.block()));
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
