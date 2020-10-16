package com.jkstack.dsm.service.directory.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jkstack.dsm.common.BaseEntity;

/**
 * 服务类别
 * @author lifang
 * @since 2020/10/15
 */
@TableName(value = "dsm_service_category")
public class ServiceCategoryEntity extends BaseEntity {


    /**
     * 名称
     */
    private String name;

    /**
     * 所属类别ID（父节点）
     */
    private Long parentId;

    /**
     * 描述
     */
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
