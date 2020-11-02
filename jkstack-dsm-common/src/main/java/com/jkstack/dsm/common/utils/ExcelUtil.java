package com.jkstack.dsm.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import com.jkstack.dsm.common.annotation.ExcelSelect;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @author lifang
 * @since 2020/10/30
 */
public final class ExcelUtil {

    /**
     * @param exportParams 导出参数
     * @param pojoClass 导出对应类
     * @param data 数据
     * @return 表格
     */
    public static Workbook exportExcel(ExportParams exportParams, Class<?> pojoClass, Collection<?> data) {
        return exportExcel(exportParams, pojoClass, data, 2);
    }

    /**
     * @param exportParams 导出参数
     * @param pojoClass 导出对应类
     * @param data 数据
     * @param firstRow 数据开始行（从0开始）
     * @return 表格
     */
    public static Workbook exportExcel(ExportParams exportParams, Class<?> pojoClass, Collection<?> data, int firstRow) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, data);
        Field[] fileds = PoiPublicUtil.getClassFields(pojoClass);
        for (int col = 0; col < fileds.length; col++) {
            Field field = fileds[col];
            if (field.getAnnotation(Excel.class) != null && field.getAnnotation(ExcelSelect.class) != null) {
                addSelectList(workbook, firstRow,  col, field.getAnnotation(ExcelSelect.class).select());
            }
        }
        return workbook;
    }


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
     * @param workbook 表格
     * @param firstRow 开始行号(从0开始)
     * @param col      下拉选项列
     * @param options  下拉内容
     */
    public static void addSelectList(Workbook workbook, int firstRow, int col, String[] options) {
        addSelectList(workbook.getSheetAt(0), firstRow, col, options);
    }
}
