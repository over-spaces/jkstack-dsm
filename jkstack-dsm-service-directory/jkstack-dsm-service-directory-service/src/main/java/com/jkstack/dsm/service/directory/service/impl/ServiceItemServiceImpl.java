package com.jkstack.dsm.service.directory.service.impl;

import com.jkstack.dsm.common.service.CommonServiceImpl;
import com.jkstack.dsm.service.directory.entity.ServiceItemEntity;
import com.jkstack.dsm.service.directory.mapper.ServiceItemMapper;
import com.jkstack.dsm.service.directory.service.ServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author lifang
 * @since 2020/10/15
 */
@Service
public class ServiceItemServiceImpl extends CommonServiceImpl<ServiceItemMapper, ServiceItemEntity> implements ServiceItemService {

    @Autowired
    private ServiceItemService serviceItemService;

    @Override
    public void deleteByIds(List<Long> serviceItemIds) {
        if(CollectionUtils.isEmpty(serviceItemIds)) {
            return;
        }
        //TODO 校验是否允许被删除
        serviceItemService.deleteByIds(serviceItemIds);
    }
}
