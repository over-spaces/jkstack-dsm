package com.jkstack.dsm;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用于管理和监控SpringBoot应用程序
 */
@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@EnableAdminServer
@EnableFeignClients(basePackages = "com.jkstack.dsm")
@EnableDiscoveryClient
@MapperScan("com.jkstack.dsm.admin.mapper")
public class JKStackDsmAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(JKStackDsmAdminApplication.class, args);
	}

}
