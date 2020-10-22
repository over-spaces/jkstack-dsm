package com.jkstack.dsm.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.jkstack.dsm.common.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * 工作组
 * @author lifang
 * @since 2020/10/12
 */
@Setter
@Getter
@Table(value = "dsm_work_group")
@TableName(value = "dsm_work_group")
public class WorkGroupEntity extends BaseEntity {

    @Column(length = 64, comment = "工作组名称")
    private String name;

}
