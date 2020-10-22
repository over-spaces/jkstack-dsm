package com.jkstack.dsm.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author lifang
 * @since 2020/10/13
 */
@Component
@Configuration
public class UserConfigProperties {

    /**
     * 用户初始化密码,默认为123456，可通过配置中心修改
     */
    @Value("${dsm.user.init-password:123456}")
    private String initPassword;

    public String getInitPassword() {
        return initPassword;
    }
}
