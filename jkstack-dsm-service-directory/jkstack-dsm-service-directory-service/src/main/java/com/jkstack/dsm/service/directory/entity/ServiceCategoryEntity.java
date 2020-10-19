package com.jkstack.dsm.service.directory.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jkstack.dsm.common.BaseEntity;
import com.jkstack.dsm.common.annotation.TableBusinessId;
import lombok.Getter;
import lombok.Setter;

/**
 * 服务类别
 * @author lifang
 * @since 2020/10/15
 */
@Getter
@Setter
@TableName(value = "dsm_service_category")
public class ServiceCategoryEntity extends BaseEntity {

    @TableBusinessId
    @TableField(fill = FieldFill.INSERT)
    private String serviceCategoryId;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属类别ID（父节点）
     */
    private String parentServiceCategoryId;

    /**
     * 描述
     */
    private String description;

}
