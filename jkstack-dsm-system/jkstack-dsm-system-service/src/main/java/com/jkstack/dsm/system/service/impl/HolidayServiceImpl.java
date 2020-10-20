package com.jkstack.dsm.system.service.impl;

import com.jkstack.dsm.common.service.CommonServiceImpl;
import com.jkstack.dsm.system.entity.HolidayEntity;
import com.jkstack.dsm.system.mapper.HolidayMapper;
import com.jkstack.dsm.system.service.HolidayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Holiday)表服务实现类
 *
 * @author lifang
 * @since 2020-10-19 18:30:15
 */
@Service
public class HolidayServiceImpl extends CommonServiceImpl<HolidayMapper, HolidayEntity> implements HolidayService {

    @Resource
    private HolidayMapper holidayMapper;


}