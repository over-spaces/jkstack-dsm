package com.jkstack.dsm.service.directory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jkstack.dsm.service.directory.entity.ServiceCategoryEntity;

import java.util.List;

/**
 * @author lifang
 * @since 2020/10/15
 */
public interface ServiceCategoryService extends IService<ServiceCategoryEntity>{

    void deleteByIds(List<Long> serviceCategoryIds);

}
