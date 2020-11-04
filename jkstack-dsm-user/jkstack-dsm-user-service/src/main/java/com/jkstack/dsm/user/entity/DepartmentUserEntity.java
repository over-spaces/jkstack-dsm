package com.jkstack.dsm.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.jkstack.dsm.common.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 所属下用户
 * @author lifang
 * @since 2020/10/12
 */
@Setter
@Getter
@NoArgsConstructor
@TableName(value = "dsm_department_user")
@Table(name = "dsm_department_user")
public class DepartmentUserEntity extends BaseEntity{

    @Column(length = 64, comment = "用户ID")
    @Index(value = "idx_user_id")
    private String userId;

    @Column(length = 64, comment = "部门ID")
    @Index(value = "idx_department_id")
    private String departmentId;

    @Column(comment = "排序")
    private Integer sort;

    public DepartmentUserEntity(String userId, String departmentId){
        this.userId = userId;
        this.departmentId = departmentId;
        this.sort = 1;
    }
}
