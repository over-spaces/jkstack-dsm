package com.jkstack.dsm.sheet.service.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: itsm
 * @description:
 * @author: DengMin
 * @create: 2020-09-04 17:24
 **/
@Component
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioConf {

    private String accessKey;

    private String secretKey;

    private String endpoint;

    private int isOpen;

    public boolean getIsOpen() {
        return this.isOpen==1;
    }
}
