package com.jkstack.dsm.common.lr;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.jkstack.dsm.common.BaseEntity;

/**
 * @author lifang
 * @since 2020/10/23
 */
public abstract class LRTreeNodeEntity extends BaseEntity {

    @Column(comment = "LR算法树-右值")
    private Integer rgt;

    @Column(comment = "LR算法树-左值")
    private Integer lft;

    @Column(comment = "LR算法树-深度")
    private Integer deep;

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
}
