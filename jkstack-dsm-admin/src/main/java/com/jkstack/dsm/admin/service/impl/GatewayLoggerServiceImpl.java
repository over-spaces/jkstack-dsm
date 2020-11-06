package com.jkstack.dsm.admin.service.impl;

import com.jkstack.dsm.admin.entity.GatewayLoggerEntity;
import com.jkstack.dsm.admin.mapper.GatewayLoggerMapper;
import com.jkstack.dsm.admin.service.GatewayLoggerService;
import com.jkstack.dsm.common.service.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lifang
 * @since 2020/11/6
 */
@Service
public class GatewayLoggerServiceImpl extends CommonServiceImpl<GatewayLoggerMapper, GatewayLoggerEntity> implements GatewayLoggerService {

    @Autowired
    private GatewayLoggerMapper gatewayLoggerMapper;
}
