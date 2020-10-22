package com.jkstack.dsm.common.lr;

/**
 * LR算法
 * @author lifang
 * @since 2020/10/21
 */
public interface LRNode {

    String getBusinessId();
    Integer getRight();
    Integer getLeft();
    Integer getDeep();
    Long getParentNodeBusinessId();
    LRNode getParentNode();

    void setRight(Integer right);
    void setLeft(Integer left);
    void setDeep(Integer deep);
    void setBusinessId(String id);
    void setParentNode(LRNode node);
    void setParentNodeBusinessId(String parentNodeId);

}
