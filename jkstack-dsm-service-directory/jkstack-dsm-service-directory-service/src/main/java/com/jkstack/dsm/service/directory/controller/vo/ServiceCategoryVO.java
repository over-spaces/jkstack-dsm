package com.jkstack.dsm.service.directory.controller.vo;

import com.jkstack.dsm.service.directory.entity.ServiceCategoryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lifang
 * @since 2020/10/15
 */
@Data
@NoArgsConstructor
@ApiModel
public class ServiceCategoryVO implements Serializable {

    @ApiModelProperty(value = "服务类别业务ID, 为空表示新建服务类别", example = "")
    private String serviceCategoryId;

    /**
     * 名称
     */
    @NotNull(message = "服务类别名称不允许为空!")
    @ApiModelProperty(value = "服务类别名称", required = true)
    private String name;

    /**
     * 所属类别ID
     */
    @ApiModelProperty(value = "服务类别名称", required = true)
    private String parentServiceCategoryId;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    public ServiceCategoryEntity toServiceCategoryEntity(){
        ServiceCategoryEntity serviceCategoryEntity = new ServiceCategoryEntity();
        serviceCategoryEntity.setName(this.name);
        serviceCategoryEntity.setDescription(this.description);
        serviceCategoryEntity.setParentServiceCategoryId(this.parentServiceCategoryId);
        return serviceCategoryEntity;
    }


    public ServiceCategoryVO(ServiceCategoryEntity serviceCategoryEntity){
        this.serviceCategoryId = serviceCategoryEntity.getServiceCategoryId();
        this.name = serviceCategoryEntity.getName();
        this.parentServiceCategoryId = serviceCategoryEntity.getParentServiceCategoryId();
        this.description = serviceCategoryEntity.getDescription();
    }
}