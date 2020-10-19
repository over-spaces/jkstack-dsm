package com.jkstack.dsm.user.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lifang
 * @since 2020/10/19
 */
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private boolean enable;

    private String title;

    private String description;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
