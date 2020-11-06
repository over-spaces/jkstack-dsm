package com.jkstack.dsm.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 列表数据返回结果VO
 * @author lifang
 * @since 2020/10/16
 */
@Setter
@Getter
@ApiModel
public class PageResult<T> implements Serializable {

    @ApiModelProperty(value = "总记录数")
    private long total;

    @ApiModelProperty(value = "总页数")
    private long page;

    @ApiModelProperty(value = "每页记录数")
    private long pageSize;

    @ApiModelProperty(value = "记录")
    private Collection<T> records;

    private Map<String, Object> expand = Maps.newHashMap();

    public PageResult(){
    }

    public PageResult(IPage page, Collection<T> records){
        this.page = page.getPages();
        this.total = page.getTotal();
        this.pageSize = page.getSize();
        this.records = records;
    }
}
