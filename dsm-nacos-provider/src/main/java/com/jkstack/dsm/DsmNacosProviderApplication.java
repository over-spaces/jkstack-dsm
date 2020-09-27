package com.jkstack.dsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DsmNacosProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DsmNacosProviderApplication.class, args);
	}

}
