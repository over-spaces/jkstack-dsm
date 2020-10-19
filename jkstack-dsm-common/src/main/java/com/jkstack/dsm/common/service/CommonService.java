package com.jkstack.dsm.common.service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author lifang
 * @since 2020/10/19
 */
public interface CommonService<T> extends IService<T> {

    /**
     * 根据业务ID查询
     * @param businessId 表的业务ID，非主键ID
     * @return
     */
    T getByBusinessId(String businessId);

}
