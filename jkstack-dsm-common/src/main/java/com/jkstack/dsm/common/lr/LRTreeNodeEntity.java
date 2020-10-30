package com.jkstack.dsm.common.lr;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.jkstack.dsm.common.BaseEntity;

/**
 * LR算法基础类
 * @author lifang
 * @since 2020/10/23
 */
public abstract class LRTreeNodeEntity extends BaseEntity {

    @Column(length = 64, comment = "名称")
    private String name;

    @Column(comment = "LR算法树-右值")
    private Integer rgt;

    @Column(comment = "LR算法树-左值")
    private Integer lft;

    @Column(comment = "LR算法树-深度")
    private Integer deep = 0;

    @Column(length = 300, comment = "名称全路径")
    private String fullPathName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRgt() {
        return rgt;
    }

    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }

    public Integer getLft() {
        return lft;
    }

    public void setLft(Integer lft) {
        this.lft = lft;
    }

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public String getFullPathName() {
        return fullPathName;
    }

    public void setFullPathName(String fullPathName) {
        this.fullPathName = fullPathName;
    }
}
