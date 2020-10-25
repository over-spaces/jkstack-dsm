package com.jkstack.dsm.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lifang
 * @since 2020/10/13
 */
@Data
@ApiModel(description = "列表分页查询类")
public class PageVO implements Serializable {

    /**
     * 分页，当前页数(从1开始)
     */
    @ApiModelProperty(value = "当前页", example = "1")
    private int pageNo;

    /**
     * 分页，每页记录数
     */
    @ApiModelProperty(value = "每页记录数", example = "15")
    private int pageSize;

    /**
     * 查询条件
     */
    @ApiModelProperty(value = "简单查询条件", example = "")
    private String condition;

}
