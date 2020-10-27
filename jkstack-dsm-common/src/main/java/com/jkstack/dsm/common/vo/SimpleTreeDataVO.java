package com.jkstack.dsm.common.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author lifang
 * @since 2020/10/15
 */
@ApiModel
public class SimpleTreeDataVO<T> extends SimpleDataVO{

    @ApiModelProperty("父节点ID")
    private String parentId;

    private int deep;

    private int sort;

    @ApiModelProperty(value = "子节点列表")
    private List<T> children = Lists.newArrayList();

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
