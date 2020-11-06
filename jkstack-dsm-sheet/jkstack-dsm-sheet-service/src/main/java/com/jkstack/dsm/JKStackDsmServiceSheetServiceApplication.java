package com.jkstack.dsm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.jkstack.dsm")
@EnableDiscoveryClient
@MapperScan("com.jkstack.dsm.sheet.mapper")
public class JKStackDsmServiceSheetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JKStackDsmServiceSheetServiceApplication.class, args);
	}
}
