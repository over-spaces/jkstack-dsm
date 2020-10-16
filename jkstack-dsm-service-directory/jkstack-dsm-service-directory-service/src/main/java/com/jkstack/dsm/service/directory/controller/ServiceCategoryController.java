package com.jkstack.dsm.service.directory.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkstack.dsm.common.BaseController;
import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.service.directory.controller.vo.ServiceCategoryTreeVO;
import com.jkstack.dsm.service.directory.controller.vo.ServiceCategoryVO;
import com.jkstack.dsm.service.directory.entity.ServiceCategoryEntity;
import com.jkstack.dsm.service.directory.service.ServiceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 服务类别
 * @author lifang
 * @since 2020/10/15
 */

@Api(tags = "服务类别", value = "服务类别管理")
@RestController
@RequestMapping("/service/category")
public class ServiceCategoryController extends BaseController {

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @ApiOperation(value = "查询服务类别列表")
    @PostMapping("/getServiceCategoryList")
    public ResponseResult<ServiceCategoryEntity> getServiceCategoryList(@RequestBody PageVO pageVO){

        Page<ServiceCategoryEntity> page = serviceCategoryService.page(new Page<>(pageVO.getPageNo(), pageVO.getPageSize()));

        List<ServiceCategoryEntity> records = page.getRecords();

        return ResponseResult.success(records);
    }

    @ApiOperation(value = "新建/修改服务类别列表")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody ServiceCategoryVO serviceCategoryVO){
        return ResponseResult.success();
    }

    @ApiOperation(value = "批量删除服务类别")
    @PostMapping("/deleteServiceCategory")
    public ResponseResult deleteServiceCategory(){
        return ResponseResult.success();
    }

    @ApiOperation(value = "下载服务类别导入模板")
    @GetMapping("/downloadImportTemplate")
    public ResponseResult downloadImportTemplate(HttpServletResponse response){
        return ResponseResult.success();
    }

    @ApiOperation(value = "导出服务类别")
    @GetMapping("/exportToExcel")
    public ResponseResult exportToExcel(HttpServletResponse response){
        return ResponseResult.success();
    }

    @ApiOperation(value = "导入服务类别")
    @PostMapping("/excelImport")
    public ResponseResult excelImport(@RequestParam(value = "file") MultipartFile file){
        return ResponseResult.success();
    }
}
