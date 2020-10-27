package com.jkstack.dsm.user.service.impl;

import com.jkstack.dsm.common.lr.LRNode;
import com.jkstack.dsm.common.lr.LRNodeTree;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import com.jkstack.dsm.user.mapper.LRTreeDao;
import com.jkstack.dsm.user.mapper.LRTreeDepartmentDaoImpl;
import com.jkstack.dsm.user.service.LRTreeService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author lifang
 * @since 2020/10/26
 */
@Service
public class LRTreeServiceImpl implements LRTreeService {

    private static final Logger logger = LoggerFactory.getLogger(LRTreeServiceImpl.class);

    @Autowired
    private LRTreeDepartmentDaoImpl lrTreeDepartmentDao;

    public LRTreeDao getLRTreeDAO(Class cls){
        if(cls == DepartmentEntity.class) {
            return lrTreeDepartmentDao;
        }
        return null;
    }

    /**
     * 一次性批量更新指定类的所有节点的L/R值。
     */
    @Override
    public void updateAllNodeLR(Class cls) {
        //1.采用全部加载到内存中的方式，应该会快一些。
        LRNode rootNode = getLRTreeDAO(cls).getVirtualRootNode();
        rootNode.setLft(1);
        rootNode.setRgt(2);
        rootNode.setDeep(0);

        List<LRNode> allNodes = getLRTreeDAO(cls).fullLoad();
        logger.debug("finished full load.");

        if(CollectionUtils.isEmpty(allNodes)) {
            return;
        }

        LRNodeTree lrNodeTreeRoot = buildNodeTree(rootNode, allNodes);

        if(lrNodeTreeRoot == null){
            logger.error("LR算法刷新失败，可能是上下级关系出现死循环了, class:{}", cls);
            return;
        }

        logger.debug("finished build tree.");

        //2. 计算所有L/R值
        lrNodeTreeRoot.setLft(1);
        lrNodeTreeRoot.setDeep(0);
        updateTreeNodeLR(lrNodeTreeRoot);

        logger.debug("finished all nodes update.");

        //3. 批量更新到数据库中
        getLRTreeDAO(cls).batchUpdate(tree2list(lrNodeTreeRoot));
        logger.debug("finished store into db.");
    }

    private LRNodeTree buildNodeTree(LRNode rootNode, Collection<LRNode> allLRNodes) {
        try {
            LRNodeTree treeRootNode = new LRNodeTree(rootNode);
            Map<Integer, List<LRNodeTree>> treeNodeMap = new HashMap<>(100);
            int maxLevel = 0;
            for (LRNode lrNode : allLRNodes) {
                LRNodeTree treeNode = new LRNodeTree(lrNode);
                int level = treeNode.getDeep();
                if (level > maxLevel) {
                    maxLevel = level;
                }

                if (!treeNodeMap.containsKey(level)) {
                    treeNodeMap.put(level, new ArrayList<>());
                }
                treeNodeMap.get(level).add(treeNode);
            }

            for (int l = maxLevel; l > 1; l--) {
                List<LRNodeTree> treeNodes = treeNodeMap.get(l);
                logger.debug("get level {} count {}", l, treeNodes.size());
                for (LRNodeTree lrNodeTree : treeNodes) {
                    LRNodeTree parentNode = findParentNode(lrNodeTree.getParentNodeBusinessId(), treeNodeMap.get(l - 1));
                    if (parentNode != null) {
                        parentNode.getChildren().add(lrNodeTree);
                        lrNodeTree.setParentNode(parentNode);
                    }
                }
            }

            treeRootNode.getChildren().addAll(treeNodeMap.get(1));
            for (LRNode lrNode : treeRootNode.getChildren()) {
                lrNode.setParentNode(treeRootNode);
            }
            logger.debug("get tree root node children size: {}", treeRootNode.getChildren().size());
            return treeRootNode;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    protected int updateTreeNodeLR(LRNodeTree lrNodeTree) {
        int seed = lrNodeTree.getLft();
        for (LRNodeTree lrNodeTreeChild : lrNodeTree.getChildren()) {
            seed++;
            lrNodeTreeChild.setLft(seed);
            seed = updateTreeNodeLR(lrNodeTreeChild);
        }
        seed++;
        lrNodeTree.setRgt(seed);
        return seed;
    }

    protected List<LRNode> tree2list(LRNodeTree lrNodeTree) {
        List<LRNode> allLRNodes = new ArrayList<>(200);
        if (StringUtils.isBlank(lrNodeTree.getBusinessId())) {
            allLRNodes.add(lrNodeTree);
        }

        if (!lrNodeTree.getChildren().isEmpty()) {
            allLRNodes.addAll(lrNodeTree.getChildren());
        }

        for (LRNodeTree lrNodeTreeChild : lrNodeTree.getChildren()) {
            if (!lrNodeTreeChild.getChildren().isEmpty()) {
                allLRNodes.addAll(tree2list(lrNodeTreeChild));
            }
        }
        return allLRNodes;
    }

    protected LRNodeTree findParentNode(String parentId, Collection<LRNodeTree> treeNodes) {
        for (LRNodeTree lrNodeTree : treeNodes) {
            if (Objects.equals(parentId, lrNodeTree.getBusinessId())) {
                return lrNodeTree;
            }
        }
        return null;
    }
}
