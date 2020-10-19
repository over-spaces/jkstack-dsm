package com.jkstack.dsm.service.directory.service.impl;

import com.jkstack.dsm.common.service.CommonServiceImpl;
import com.jkstack.dsm.service.directory.entity.ServiceCategoryEntity;
import com.jkstack.dsm.service.directory.mapper.ServiceCategoryMapper;
import com.jkstack.dsm.service.directory.service.ServiceCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lifang
 * @since 2020/10/15
 */
@Service
public class ServiceCategoryServiceImpl extends CommonServiceImpl<ServiceCategoryMapper, ServiceCategoryEntity> implements ServiceCategoryService {

    @Override
    public void deleteByIds(List<Long> serviceCategoryIds) {
        // TODO 校验允许删除
        removeByIds(serviceCategoryIds);
    }
}
