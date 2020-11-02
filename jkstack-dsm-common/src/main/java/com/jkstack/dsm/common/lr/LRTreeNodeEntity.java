package com.jkstack.dsm.common.lr;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.jkstack.dsm.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * LR算法基础类
 * @author lifang
 * @since 2020/10/23
 */
@Setter
@Getter
public abstract class LRTreeNodeEntity extends BaseEntity {

    @Column(length = 64, comment = "名称")
    private String name;

    @Column(comment = "LR算法树-右值")
    private Integer rgt;

    @Column(comment = "LR算法树-左值")
    private Integer lft;

    @Column(comment = "LR算法树-深度")
    private Integer deep;

    @Column(length = 300, comment = "名称全路径")
    private String fullPathName;
}
