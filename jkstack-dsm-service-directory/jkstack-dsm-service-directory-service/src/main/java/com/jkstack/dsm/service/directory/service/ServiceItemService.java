package com.jkstack.dsm.service.directory.service;

import com.jkstack.dsm.common.service.CommonService;
import com.jkstack.dsm.service.directory.entity.ServiceItemEntity;

import java.util.List;

/**
 * @author lifang
 * @since 2020/10/15
 */
public interface ServiceItemService extends CommonService<ServiceItemEntity> {

    void deleteByIds(List<Long> serviceItemIds);

}
