package com.jkstack.dsm.common.lr;

import com.jkstack.dsm.common.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author lifang
 * @since 2020/10/26
 */
public class LRNodeTree extends LRNode {

    private LRNodeTree parentNodeTree;

    private List<LRNodeTree> children = new ArrayList<>();

    public LRNodeTree(LRNode node) {
        this(node, 0);
    }

    public LRNodeTree(LRNode node, int index) throws ServiceException {
        super.setId(node.getId());
        super.setParentId(node.getParentId());
        super.setName(node.getName());
        super.setDeep(node.getDeep());
        super.setLft(node.getLft());
        super.setRgt(node.getRgt());
        super.setFullPathName(node.getFullPathName());
        if (node.getParentNode() != null) {
            if (index > 50) {
                throw new ServiceException("部门上下级关系错误!");
            }
            this.parentNodeTree = new LRNodeTree(node.getParentNode(), index + 1);
        } else {
            this.parentNodeTree = null;
        }
    }


    @Override
    public Integer getDeep() {
        if (StringUtils.isBlank(this.getId())) {
            return 0;
        }
        if (parentNodeTree == null) {
            return 1;
        } else {
            return parentNodeTree.getDeep() + 1;
        }
    }

    public List<LRNodeTree> getChildren() {
        return children;
    }

    public void setChildren(List<LRNodeTree> children) {
        this.children = children;
    }

    public LRNodeTree getParentNodeTree() {
        return parentNodeTree;
    }

    public void setParentNodeTree(LRNodeTree parentNodeTree) {
        this.parentNodeTree = parentNodeTree;
    }
}
