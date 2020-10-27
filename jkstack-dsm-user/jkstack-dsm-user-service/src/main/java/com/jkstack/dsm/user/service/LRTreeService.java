package com.jkstack.dsm.user.service;

import com.jkstack.dsm.common.lr.LRNode;

/**
 * @author lifang
 * @since 2020/10/26
 */
public interface LRTreeService {

    <T extends LRNode> void updateAllNodeLR(Class<T> cls);

}
