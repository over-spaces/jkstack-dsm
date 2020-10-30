package com.jkstack.dsm.common.utils;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;

/**
 * @author lifang
 * @since 2020/10/30
 */
public final class ExcelUtil {

    /**
     * 给excel添加下拉选项列
     *
     * @param sheet    表格
     * @param firstRow 开始行号(从0开始)
     * @param col      下拉选项列
     * @param options  下拉内容
     */
    public static void addSelectList(Sheet sheet, int firstRow, int col, String[] options) {
        //  生成下拉列表
        //  只对(x，x)单元格有效
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(firstRow, 65535, col, col);
        //  生成下拉框内容
        DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(options);
        HSSFDataValidation dataValidation = new HSSFDataValidation(cellRangeAddressList, dvConstraint);
        //  对sheet页生效
        sheet.addValidationData(dataValidation);
    }

    /**
     * 给excel添加下拉选项列
     *
     * @param workbook    表格
     * @param firstRow 开始行号(从0开始)
     * @param col      下拉选项列
     * @param options  下拉内容
     */
    public static void addSelectList(Workbook workbook, int firstRow, int col, String[] options) {
        addSelectList(workbook.getSheetAt(0), firstRow, col, options);
    }
}
