package com.jkstack.dsm.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.jkstack.dsm.common.BaseEntity;
import com.jkstack.dsm.common.annotation.TableBusinessId;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色
 * @author lifang
 * @since 2020/10/12
 */
@Setter
@Getter
@Table(name = "dsm_role")
@TableName(value = "dsm_role")
public class RoleEntity extends BaseEntity {

    @TableBusinessId
    @TableField(fill = FieldFill.INSERT)
    @Column(length = 64, comment = "角色ID")
    @Index(value = "idx_role_id")
    private String roleId;

    @Column(length = 64, comment = "角色名称")
    private String name;

}
