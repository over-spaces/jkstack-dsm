package com.jkstack.dsm.service.directory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jkstack.dsm.common.BaseEntity;
import lombok.Data;

/**
 * 服务类别
 * @author lifang
 * @since 2020/10/15
 */
@Data
@TableName(value = "dsm_service_category")
public class ServiceCategoryEntity extends BaseEntity {

    /**
     * 服务类别ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String serviceCategoryId;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属类别ID（父节点）
     */
    private Long parentServiceCategoryId;

    /**
     * 描述
     */
    private String description;
}
