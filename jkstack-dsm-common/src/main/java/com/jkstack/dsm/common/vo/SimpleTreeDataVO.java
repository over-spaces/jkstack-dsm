package com.jkstack.dsm.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author lifang
 * @since 2020/10/15
 */
public abstract class SimpleTreeDataVO<T> implements Serializable {

    private String id;

    private String name;

    private List<T> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
