package com.jkstack.dsm.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.jkstack.dsm.common.annotation.TableBusinessId;
import com.jkstack.dsm.common.lr.LRTreeNodeEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 部门实体
 * @author lifang
 * @since 2020/10/21
 */
@Setter
@Getter
@Table(name = "dsm_department")
@TableName(value = "dsm_department")
public class DepartmentEntity extends LRTreeNodeEntity{

    @TableBusinessId(prefix = "dept")
    @TableField(fill = FieldFill.INSERT)
    @Column(length = 64, comment = "部门ID")
    @Index(value = "idx_department_id")
    @Unique
    private String departmentId;

    @Column(length = 64, comment = "父部门ID")
    @Index(value = "idx_parent_department_id")
    private String parentDepartmentId;

    @Column(comment = "排序")
    private Integer sort;
}
