package com.jkstack.dsm.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.jkstack.dsm.common.BaseEntity;
import com.jkstack.dsm.common.annotation.TableBusinessId;
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

    @TableBusinessId
    @TableField(fill = FieldFill.INSERT)
    @Column(length = 64, comment = "用户ID")
    @Unique
    @Index("idx_work_group_id")
    private String workGroupId;

    @Column(length = 64, comment = "工作组名称")
    private String name;

    @Column(length = 120, comment = "备注")
    private String description;
}
