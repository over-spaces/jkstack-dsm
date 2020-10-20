package com.jkstack.dsm.system.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jkstack.dsm.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * (Holiday)实体类
 *
 * @author lifang
 * @since 2020-10-19 18:30:12
 */
@Setter
@Getter
@TableName(value = "dsm_holiday")
public class HolidayEntity extends BaseEntity {
    private static final long serialVersionUID = -15165611345010703L;


    private String name;

    private String holidayId;

    private Date startDate;

    private Date endDate;


}