package com.jkstack.dsm.common;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 列表数据返回结果VO
 * @author lifang
 * @since 2020/10/16
 */
@ApiModel
public class PageResult<T> implements Serializable {

    @ApiModelProperty(value = "总记录数")
    private long total;

    @ApiModelProperty(value = "总页数")
    private long page;

    @ApiModelProperty(value = "记录")
    private Collection<T> records;

    private Map<String, Object> expand = Maps.newHashMap();

    public PageResult(){
    }

    public PageResult(long page, long total, Collection<T> records) {
        this.page = page;
        this.total = total;
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public Collection<T> getRecords() {
        return records;
    }

    public void setRecords(Collection<T> records) {
        this.records = records;
    }

    public Map<String, Object> getExpand() {
        return expand;
    }

    public void setExpand(Map<String, Object> expand) {
        this.expand = expand;
    }
}
