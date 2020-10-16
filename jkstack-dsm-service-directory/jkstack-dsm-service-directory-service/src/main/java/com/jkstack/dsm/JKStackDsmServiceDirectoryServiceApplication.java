package com.jkstack.dsm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.jkstack.dsm")
@EnableDiscoveryClient
@MapperScan(basePackages = "com.jkstack.dsm.service.directory.mapper")
public class JKStackDsmServiceDirectoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JKStackDsmServiceDirectoryServiceApplication.class, args);
	}

}
