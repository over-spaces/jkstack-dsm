package com.jkstack.dsm.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.jkstack.dsm")
public class JKStackDsmGatewayApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JKStackDsmGatewayApplication.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(RequestPredicates.GET("/swagger").and(RequestPredicates.GET("/swagger-ui")),
                req -> ServerResponse.temporaryRedirect(URI.create("/swagger-ui/index.html")).build());
    }
}
