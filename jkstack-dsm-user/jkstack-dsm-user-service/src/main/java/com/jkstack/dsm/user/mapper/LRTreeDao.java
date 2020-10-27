package com.jkstack.dsm.user.mapper;


import com.jkstack.dsm.common.lr.LRNode;

import java.util.List;

/**
 * @author lifang
 * @since 2020-10-26
 */
public interface LRTreeDao {

    LRNode getVirtualRootNode();


    void updateNodeLR(String id, Integer left, Integer right, Integer deep);


    void batchUpdateNodesLR(int baseValue, int step);

    void batchUpdateChildrenLRToNegative(int left, int right);

    void batchUpdateChildrenLRByDiff(int left, int right, int diff, int diffDeep);

    long count();

    List<LRNode> fullLoad();

    void batchUpdate(List<LRNode> allNodes);

}
