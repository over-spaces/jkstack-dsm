package com.jkstack.dsm.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus分页配置，actable自动建表配置。
 * @author lifang
 * @since 2020/11/6
 */
@Configuration
@ComponentScan({"com.gitee.sunchenbin.mybatis.actable.manager.*"})
@MapperScan({"com.gitee.sunchenbin.mybatis.actable.dao.*"})
public class MybatisPlusConfig {

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
