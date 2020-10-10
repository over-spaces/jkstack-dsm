package com.jkstack.dsm;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用于管理和监控SpringBoot应用程序
 */
@SpringBootApplication
@EnableAdminServer
public class JKStackDsmAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(JKStackDsmAdminApplication.class, args);
	}

}
