package com.jkstack.dsm.service.directory.controller.vo;

import com.jkstack.dsm.common.PageResult;
import io.swagger.annotations.ApiModel;

/**
 * @author lifang
 * @since 2020/10/19
 */
@ApiModel
public class ServiceItemVO extends PageResult {

    private String name;

    private String description;

    private String serviceTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }
}
