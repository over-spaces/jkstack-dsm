package com.jkstack.dsm.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jkstack.dsm.common.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 部门类
 * @author lifang
 * @since 2020/10/12
 */
@Data
@Entity
@Table(name = "t_department")
@TableName(value = "t_department")
public class DepartmentEntity extends BaseEntity {

    /**
     * 父节点ID
     */
    private Long parentId;

    @Column(length = 64)
    private String name;

}
