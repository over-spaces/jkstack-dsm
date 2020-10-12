package com.jkstack.dsm.user.entity;

import com.jkstack.dsm.common.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 工作组
 * @author lifang
 * @since 2020/10/12
 */
@Data
@Entity
@Table(name = "t_work_group")
public class WorkGroupEntity extends BaseEntity {

    private String name;

}
