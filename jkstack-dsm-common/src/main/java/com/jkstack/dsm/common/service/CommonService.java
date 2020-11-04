package com.jkstack.dsm.common.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * @author lifang
 * @since 2020/10/19
 */
public interface CommonService<T> extends IService<T> {

    /**
     * 根据业务ID查询
     * @param businessId 表的业务ID，非主键ID
     */
    T getByBusinessId(String businessId);

    /**
     * 根据业务ID批量查询
     * @param businessIds 表业务ID集合
     */
    List<T> listByBusinessIds(Collection<String> businessIds);

    /**
     * 根据业务ID删除记录
     * @param businessId 表的业务ID，非主键ID
     */
    void removeByBusinessId(String businessId);

    /**
     * 根据业务ID删除记录
     * @param businessIds 表的业务ID，非主键ID
     */
    void removeByBusinessIds(Collection<String> businessIds);

    /**
     * 根据业务ID更新
     * @param entity
     */
    void updateByBusinessId(T entity);

    /**
     * 根据业务ID更新
     * @param entityList 实体类列表
     */
    boolean updateBatchByBusinessId(Collection<T> entityList);
}
