package com.jkstack.dsm.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author lifang
 * @since 2020/10/13
 */
@Configuration
public class UserConfig {

    /**
     * 用户初始化密码,默认为123456，可通过配置中心修改
     */
    @Value("${dsm.user.init-password:123456}")
    public String initPassword;

    public String getInitPassword() {
        return initPassword;
    }
}
