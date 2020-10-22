package com.jkstack.dsm.common;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 数据导入结果
 * @author lifang
 * @since 2020/10/22
 */
@Setter
@Getter
@NoArgsConstructor
@ApiModel
public class ImportResult implements Serializable {

    /**
     * 导入总记录数
     */
    @ApiModelProperty("导入总记录数")
    private int total;

    /**
     * 导入成功的记录数
     */
    @ApiModelProperty("导入成功记录数")
    private int successCount;

    /**
     * 导入失败的记录数
     */
    @ApiModelProperty("导入失败记录数")
    private int failCount;

    /**
     * 错误数据文件路径
     */
    @ApiModelProperty("导入失败文件路径")
    private String failFilePath;

    /**
     * 错误数据文件名称
     */
    @ApiModelProperty("导入失败名称")
    private String failFileName;

    public ImportResult(ExcelImportResult excelImportResult){
        this.successCount = excelImportResult.getList().size();
        this.failCount = excelImportResult.getFailList().size();
        this.total = this.successCount + this.failCount;
    }
}
