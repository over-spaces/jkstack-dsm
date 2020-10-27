package com.jkstack.dsm.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.jkstack.dsm")
public class JKStackDsmGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(JKStackDsmGatewayApplication.class, args);
    }


}
