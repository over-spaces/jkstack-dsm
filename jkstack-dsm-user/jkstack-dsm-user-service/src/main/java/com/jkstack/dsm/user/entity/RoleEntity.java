package com.jkstack.dsm.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jkstack.dsm.common.BaseEntity;
import lombok.Data;

/**
 * @author lifang
 * @since 2020/10/12
 */
@Data
@TableName(value = "t_role")
public class RoleEntity extends BaseEntity {

    private String name;

}
