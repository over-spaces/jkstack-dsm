package com.jkstack.dsm.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.jkstack.dsm.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色成员
 * @author lifang
 * @since 2020/10/12
 */
@Setter
@Getter
@Table(value = "dsm_user_role")
@TableName(value = "dsm_user_role")
public class UserRoleEntity extends BaseEntity {

    @Column(length = 64, comment = "用户ID")
    @Index(value = "idx_user_id")
    private String userId;

    @Column(length = 64, comment = "角色ID")
    @Index(value = "idx_role_id")
    private String roleId;

}
