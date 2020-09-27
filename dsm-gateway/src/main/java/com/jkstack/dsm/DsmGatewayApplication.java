package com.jkstack.dsm;

import com.jkstack.dsm.filter.AuthenticateFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class DsmGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DsmGatewayApplication.class, args);
	}

	@Bean
	public AuthenticateFilter dsmFilter(){
		return new AuthenticateFilter();
	}

}
