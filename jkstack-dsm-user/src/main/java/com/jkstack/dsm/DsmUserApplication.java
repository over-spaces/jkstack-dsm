package com.jkstack.dsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.jkstack.dsm.dao")
public class DsmUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(DsmUserApplication.class, args);
	}

}
