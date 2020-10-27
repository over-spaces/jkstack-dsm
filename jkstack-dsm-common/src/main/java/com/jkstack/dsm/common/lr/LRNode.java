package com.jkstack.dsm.common.lr;

import lombok.Getter;
import lombok.Setter;

import java.util.Stack;

/**
 * LR算法
 * @author lifang
 * @since 2020/10/21
 */
@Setter
@Getter
public class LRNode {

    private String id;

    private String name;

    private String parentId;

    private Integer rgt;

    private Integer lft;

    private Integer deep;

    private LRNode parentNode;

    private String fullPathName;
}
