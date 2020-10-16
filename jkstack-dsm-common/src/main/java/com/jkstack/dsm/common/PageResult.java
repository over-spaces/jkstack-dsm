package com.jkstack.dsm.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Collection;

/**
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
    private Collection records;

    public PageResult(long page, long total, Collection records) {
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

    public Collection getRecords() {
        return records;
    }

    public void setRecords(Collection records) {
        this.records = records;
    }
}
