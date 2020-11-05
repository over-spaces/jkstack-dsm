package com.jkstack.dsm.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jkstack.dsm.common.lr.LRNode;
import com.jkstack.dsm.common.lr.LRNodeTree;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import com.jkstack.dsm.user.mapper.DepartmentMapper;
import com.jkstack.dsm.user.service.LRTreeService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lifang
 * @since 2020/10/29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LRTreeServiceImpl implements LRTreeService {

    private static final Logger logger = LoggerFactory.getLogger(LRTreeServiceImpl.class);

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 一次性批量更新指定类的所有节点的L/R值。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAllNodeLR(Class cls) {
        //1.采用全部加载到内存中的方式，应该会快一些。
        LRNode rootNode = getVirtualRootNode();
        rootNode.setLft(1);
        rootNode.setRgt(2);
        rootNode.setDeep(0);

        Collection<LRNode> allNodes = listAllNode();

        if (CollectionUtils.isEmpty(allNodes)) {
            return;
        }

        //2.设置名称全路径
        allNodes.stream().forEach(node -> {
            List<String> nameFullPathList = getNameFullPathList(node, new ArrayList<>());
            Collections.reverse(nameFullPathList);
            node.setFullPathName(nameFullPathList.stream().collect(Collectors.joining("/")));
        });

        LRNodeTree root = buildNodeTree(rootNode, allNodes);

        if (root == null) {
            logger.error("LR算法刷新失败，可能是上下级关系出现死循环了, class:{}", cls);
            return;
        }

        //3. 计算所有L/R值
        root.setLft(1);
        root.setDeep(0);
        calcNodeLR(root);

        //4. 批量更新到数据库中
        // getLRTreeDAO(cls).batchUpdate(tree2list(root));
        batchUpdateNodeLR(tree2list(root));
        logger.debug("finished store into db.");
    }


    public void batchUpdateNodeLR(List<LRNode> allNodeList) {
        allNodeList.stream()
                .filter(node -> StringUtils.isNotBlank(node.getId()))
                .forEach(node -> {
                    LambdaUpdateWrapper<DepartmentEntity> wrapper = new LambdaUpdateWrapper<>();
                    wrapper.eq(DepartmentEntity::getDepartmentId, node.getId());
                    wrapper.set(DepartmentEntity::getDeep, node.getDeep());
                    wrapper.set(DepartmentEntity::getRgt, node.getRgt());
                    wrapper.set(DepartmentEntity::getLft, node.getLft());
                    wrapper.set(DepartmentEntity::getFullPathName, node.getFullPathName());
                    departmentMapper.update(null, wrapper);
                });
    }

    public Collection<LRNode> listAllNode() {
        List<LRNode> allNodes = departmentMapper.selectLRNodeList();
        Map<String, LRNode> nodeMap = allNodes.stream().collect(Collectors.toMap(LRNode::getId, Function.identity()));

        Collection<LRNode> nodes = nodeMap.values();
        nodes.stream()
                .filter(node -> StringUtils.isNotBlank(node.getParentId()))
                .forEach(node -> {
                    node.setParentNode(nodeMap.get(node.getParentId()));
                });
        return nodes;
    }

    private LRNode getVirtualRootNode() {
        Integer right = departmentMapper.getMaxRightValue();
        LRNode node = new LRNode();
        right = Math.max(Optional.ofNullable(right).orElse(0), 1);
        node.setRgt(right + 1);
        node.setDeep(0);
        node.setLft(1);
        return node;
    }


    private List<String> getNameFullPathList(LRNode node, List<String> nameFullPathList) {
        if (node != null && StringUtils.isNotBlank(node.getName())) {
            nameFullPathList.add(node.getName());
            if (node.getParentNode() != null) {
                getNameFullPathList(node.getParentNode(), nameFullPathList);
            }
        }
        return nameFullPathList;
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

            for (int level = maxLevel; level > 1; level--) {
                List<LRNodeTree> treeNodes = treeNodeMap.get(level);
                logger.debug("get level {} count {}", level, treeNodes.size());
                for (LRNodeTree lrNodeTree : treeNodes) {
                    LRNodeTree parentNode = findParentNode(lrNodeTree.getParentId(), treeNodeMap.get(level - 1));
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
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    protected int calcNodeLR(LRNodeTree lrNodeTree) {
        int seed = lrNodeTree.getLft();
        for (LRNodeTree lrNodeTreeChild : lrNodeTree.getChildren()) {
            seed++;
            lrNodeTreeChild.setLft(seed);
            seed = calcNodeLR(lrNodeTreeChild);
        }
        seed++;
        lrNodeTree.setRgt(seed);
        return seed;
    }

    protected List<LRNode> tree2list(LRNodeTree lrNodeTree) {
        List<LRNode> allLRNodes = new ArrayList<>(200);
        if (StringUtils.isBlank(lrNodeTree.getId())) {
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
        return treeNodes.stream()
                .filter(node -> StringUtils.equals(parentId, node.getId()))
                .findFirst()
                .orElse(null);
    }
}
