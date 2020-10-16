package com.jkstack.dsm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.jkstack.dsm")
@EnableDiscoveryClient
@MapperScan("com.jkstack.dsm.user.mapper")
public class JKStackDsmUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JKStackDsmUserServiceApplication.class, args);
	}

}
