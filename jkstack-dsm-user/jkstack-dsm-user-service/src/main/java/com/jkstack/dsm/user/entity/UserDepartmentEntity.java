package com.jkstack.dsm.user.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.jkstack.dsm.common.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * 用户所属部门
 * @author lifang
 * @since 2020/10/12
 */
@Setter
@Getter
@Table(name = "dsm_user_department")
public class UserDepartmentEntity extends BaseEntity {

    @Column(length = 64, comment = "用户ID")
    @Index(value = "idx_user_id")
    private String userId;

    @Column(length = 64, comment = "部门ID")
    @Index(value = "idx_department_id")
    private String departmentId;

}
