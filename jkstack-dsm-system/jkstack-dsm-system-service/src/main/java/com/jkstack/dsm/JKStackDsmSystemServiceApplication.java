package com.jkstack.dsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.jkstack.dsm")
@EnableDiscoveryClient
@EnableSwagger2
public class JKStackDsmSystemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JKStackDsmSystemServiceApplication.class, args);
	}

}
