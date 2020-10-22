package com.jkstack.dsm.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Collection;

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

    public void setPage(int page) {
        this.page = page;
    }

    public Collection<T> getRecords() {
        return records;
    }

    public void setRecords(Collection<T> records) {
        this.records = records;
    }
}
