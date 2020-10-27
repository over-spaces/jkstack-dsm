package com.jkstack.dsm.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.jkstack.dsm.common.annotation.TableBusinessId;
import com.jkstack.dsm.common.lr.LRNode;
import com.jkstack.dsm.common.lr.LRTreeNodeEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

/**
 * 部门实体
 * @author lifang
 * @since 2020/10/21
 */
@Setter
@Getter
@Table(name = "dsm_department")
@TableName(value = "dsm_department")
public class DepartmentEntity extends LRTreeNodeEntity implements LRNode<DepartmentEntity>{

    @TableBusinessId
    @TableField(fill = FieldFill.INSERT)
    @Column(length = 64, comment = "部门ID")
    @Index(value = "idx_department_id")
    @Unique
    private String departmentId;

    @Column(length = 64, comment = "名称")
    private String name;

    @Column(length = 64, comment = "父部门ID")
    @Index(value = "idx_parent_department_id")
    private String parentDepartmentId;

    @Column(length = 64, comment = "部门主管")
    private String leaderUserId;

    @Column(comment = "排序")
    private int sort;

    /**
     * 不需要对应表字段。
     */
    @Transient
    @TableField(exist = false)
    private DepartmentEntity parentNode;


    /**
     * 对应表业务ID
     */
    @Override
    public String getBusinessId() {
        return departmentId;
    }

    @Override
    public void setBusinessId(String businessId) {
        this.departmentId = businessId;
    }

    @Override
    public String getParentNodeBusinessId() {
        return parentDepartmentId;
    }

    @Override
    public void setParentNodeBusinessId(String parentNodeBusinessId) {
        this.parentDepartmentId = parentNodeBusinessId;
    }
}
