package com.jkstack.dsm.service.directory.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lifang
 * @since 2020/10/15
 */
@Data
@ApiModel
public class ServiceCategoryVO implements Serializable {

    /**
     * 名称
     */
    @ApiModelProperty(value = "服务类别名称", required = true)
    private String name;

    /**
     * 所属类别ID
     */
    @ApiModelProperty(value = "服务类别名称", required = true)
    private String parentId;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
}
