package com.jkstack.dsm.user.service;

import com.jkstack.dsm.common.lr.LRTreeNodeEntity;

/**
 * @author lifang
 * @since 2020/10/29
 */
public interface LRTreeService {

    /**
     * 更新LR算法值
     * @param cls LR算法类
     * @param <T> 泛型
     */
    <T extends LRTreeNodeEntity> void updateAllNodeLR(Class<T> cls);

}
