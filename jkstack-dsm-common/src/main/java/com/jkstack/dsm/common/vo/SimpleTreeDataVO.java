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
@NoArgsConstructor
public class SimpleTreeDataVO<T> extends SimpleDataVO {

    @ApiModelProperty(value = "子节点列表")
    private List<T> children = Lists.newArrayList();

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
