package com.jkstack.dsm.common.lr;

import com.jkstack.dsm.common.BaseEntity;

/**
 * LR算法
 * @author lifang
 * @since 2020/10/21
 */
public interface LRNode<T> {

    /**
     * 对应表业务ID
     */
    String getBusinessId();

    void setBusinessId(String businessId);

    Integer getRgt();
    void setRgt(Integer rgt);

    Integer getLft();
    void setLft(Integer lft);

    Integer getDeep();
    void setDeep(Integer deep);

    String getParentNodeBusinessId();
    void setParentNodeBusinessId(String parentNodeBusinessId);

    T getParentNode();

    void setParentNode(T node);

}
