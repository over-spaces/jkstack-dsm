package com.jkstack.dsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.jkstack.dsm")
@EnableDiscoveryClient
public class JkstackDsmWeixinServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JkstackDsmWeixinServiceApplication.class, args);
	}

}
