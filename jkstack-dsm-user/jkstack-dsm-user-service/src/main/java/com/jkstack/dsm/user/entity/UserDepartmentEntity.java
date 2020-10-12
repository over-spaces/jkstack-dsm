package com.jkstack.dsm.user.entity;

import com.jkstack.dsm.common.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户所属部门
 * @author lifang
 * @since 2020/10/12
 */
@Data
@Entity
@Table(name = "t_user_department")
public class UserDepartmentEntity extends BaseEntity {

    private Long userId;

    private Long deptId;

}
