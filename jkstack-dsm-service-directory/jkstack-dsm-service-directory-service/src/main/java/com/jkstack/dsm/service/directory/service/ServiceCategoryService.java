package com.jkstack.dsm.service.directory.service;

import com.jkstack.dsm.common.service.CommonService;
import com.jkstack.dsm.service.directory.entity.ServiceCategoryEntity;

import java.util.List;

/**
 * @author lifang
 * @since 2020/10/15
 */
public interface ServiceCategoryService extends CommonService<ServiceCategoryEntity> {

    void deleteByIds(List<Long> serviceCategoryIds);

}
