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
public class LRNodeTree implements LRNode<LRNodeTree>{

    private String businessId;
    private String name;
    private Integer rgt;
    private Integer lft;
    private Integer deep;
    private String parentNodeBusinessId;
    private LRNodeTree parentNode;
    private List<LRNodeTree> children = new ArrayList<>();

    public LRNodeTree(LRNode lrNode) throws Exception {
        this(lrNode, 0);
    }

    public LRNodeTree(LRNode lrNode, int index) throws ServiceException {
        this.businessId = lrNode.getBusinessId();
        if (lrNode.getParentNode() != null) {
            if(index > 50){
                throw new ServiceException("部门上下级关系错误!");
            }
            this.parentNode = new LRNodeTree((LRNode) lrNode.getParentNode(), index + 1);
        } else {
            this.parentNode = null;
        }
        this.parentNodeBusinessId = lrNode.getParentNodeBusinessId();
    }

    @Override
    public String getBusinessId() {
        return businessId;
    }

    @Override
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Override
    public Integer getRgt() {
        return rgt;
    }

    @Override
    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }

    @Override
    public Integer getLft() {
        return lft;
    }

    @Override
    public void setLft(Integer lft) {
        this.lft = lft;
    }

    @Override
    public Integer getDeep() {
        if (StringUtils.isBlank(this.businessId)) {
            return 0;
        } if (parentNode == null) {
            return 1;
        }else {
            return parentNode.getDeep() + 1;
        }
    }

    @Override
    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    @Override
    public String getParentNodeBusinessId() {
        return parentNodeBusinessId;
    }

    @Override
    public void setParentNodeBusinessId(String parentNodeBusinessId) {
        this.parentNodeBusinessId = parentNodeBusinessId;
    }

    @Override
    public LRNodeTree getParentNode() {
        return parentNode;
    }

    @Override
    public void setParentNode(LRNodeTree node) {
        this.parentNode = node;
    }

    public List<LRNodeTree> getChildren() {
        return children;
    }

    public void setChildren(List<LRNodeTree> children) {
        this.children = children;
    }
}
