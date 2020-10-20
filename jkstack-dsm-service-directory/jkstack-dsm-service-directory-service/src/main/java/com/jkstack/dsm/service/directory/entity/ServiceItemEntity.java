package com.jkstack.dsm.service.directory.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.jkstack.dsm.common.BaseEntity;
import com.jkstack.dsm.common.annotation.TableBusinessId;
import lombok.Getter;
import lombok.Setter;

/**
 * 服务项
 * @author lifang
 * @since 2020-10-15
 */
@Setter
@Getter
@TableName(value = "dsm_service_item")
public class ServiceItemEntity extends BaseEntity {

    /**
     * 服务项业务ID
     */
    @TableBusinessId
    @TableField(fill = FieldFill.INSERT)
    private String serviceItemId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 服务项类型
     */
    @EnumValue
    private ServiceItemTypeEnum serviceItemType;

    /**
     * 所属类别
     * ServiceCategoryEntity#serviceCategoryId
     */
    private String serviceCategoryId;
}