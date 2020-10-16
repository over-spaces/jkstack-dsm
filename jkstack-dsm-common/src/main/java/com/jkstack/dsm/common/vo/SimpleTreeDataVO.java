package com.jkstack.dsm.common.vo;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @author lifang
 * @since 2020/10/15
 */
public abstract class SimpleTreeDataVO<T> implements Serializable {

    private Long id;

    private String name;

    private List<T> children = Lists.newArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
