package com.jkstack.dsm.service.directory.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jkstack.dsm.common.BaseEntity;
import lombok.Data;

/**
 * 服务项
 * @author lifang
 * @since 2020-10-15
 */
@Data
@TableName(value = "dsm_service_item")
public class ServiceItemEntity extends BaseEntity {

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

}
