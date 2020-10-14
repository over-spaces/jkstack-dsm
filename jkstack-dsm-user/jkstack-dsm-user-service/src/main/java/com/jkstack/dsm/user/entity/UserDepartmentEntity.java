package com.jkstack.dsm.user.entity;

import com.jkstack.dsm.common.BaseEntity;
import lombok.Data;


/**
 * 用户所属部门
 * @author lifang
 * @since 2020/10/12
 */
@Data
public class UserDepartmentEntity extends BaseEntity {

    private Long userId;

    private Long deptId;

}
