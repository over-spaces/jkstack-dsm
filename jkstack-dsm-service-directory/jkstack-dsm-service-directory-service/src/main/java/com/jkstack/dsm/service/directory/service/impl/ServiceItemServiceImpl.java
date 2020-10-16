package com.jkstack.dsm.service.directory.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jkstack.dsm.service.directory.entity.ServiceItemEntity;
import com.jkstack.dsm.service.directory.mapper.ServiceItemMapper;
import com.jkstack.dsm.service.directory.service.ServiceItemService;
import org.springframework.stereotype.Service;

/**
 * @author lifang
 * @since 2020/10/15
 */
@Service
public class ServiceItemServiceImpl extends ServiceImpl<ServiceItemMapper, ServiceItemEntity> implements ServiceItemService {
}
