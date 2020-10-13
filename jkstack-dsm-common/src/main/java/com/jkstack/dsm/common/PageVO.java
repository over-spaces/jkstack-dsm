package com.jkstack.dsm.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lifang
 * @since 2020/10/13
 */
@Data
public class PageVO implements Serializable {

    /**
     * 分页，当前页数
     */
    private int pageNo;

    /**
     * 分页，每页记录数
     */
    private int pageSize;

    /**
     * 查询条件
     */
    private String condition;

}
