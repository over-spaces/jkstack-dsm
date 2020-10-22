package com.jkstack.dsm;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.jkstack.dsm")
@EnableDiscoveryClient
@MapperScan("com.jkstack.dsm.user.mapper")
@ComponentScan({"com.jkstack.dsm", "com.gitee.sunchenbin.mybatis.actable.manager.*"})
@MapperScan({"com.gitee.sunchenbin.mybatis.actable.dao.*"})
public class JKStackDsmUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JKStackDsmUserServiceApplication.class, args);
	}

	/**
	 * Mybatis plus 分页配置
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}
}
