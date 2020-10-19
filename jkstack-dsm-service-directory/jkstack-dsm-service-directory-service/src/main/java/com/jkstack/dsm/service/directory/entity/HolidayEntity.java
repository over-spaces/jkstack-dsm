package com.jkstack.dsm.service.directory.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jkstack.dsm.common.BaseEntity;
import com.jkstack.dsm.common.annotation.TableBusinessId;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 节假日
 * @author lifang
 * @since 2020/10/19
 */
@Getter
@Setter
@TableName("dsm_holiday")
public class HolidayEntity extends BaseEntity {

    /**
     * 节假日业务ID
     */
    @TableBusinessId
    @TableField(fill = FieldFill.INSERT)
    private String holidayId;

    /**
     * 名称
     */
    private String name;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

}
