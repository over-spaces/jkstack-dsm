package com.jkstack.dsm.service.directory.service.impl;

import com.jkstack.dsm.common.service.CommonServiceImpl;
import com.jkstack.dsm.service.directory.entity.HolidayEntity;
import com.jkstack.dsm.service.directory.mapper.HolidayMapper;
import com.jkstack.dsm.service.directory.service.HolidayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Holiday)表服务实现类
 *
 * @author lifang
 * @since 2020-10-19 18:18:46
 */
@Service
public class HolidayServiceImpl extends CommonServiceImpl<HolidayMapper, HolidayEntity> implements HolidayService {

    @Resource
    private HolidayMapper holidayMapper;


}