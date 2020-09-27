package com.jkstack.dsm;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用于管理和监控SpringBoot应用程序
 */
@SpringBootApplication
@EnableAdminServer
public class DsmAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(DsmAdminApplication.class, args);
	}

}
