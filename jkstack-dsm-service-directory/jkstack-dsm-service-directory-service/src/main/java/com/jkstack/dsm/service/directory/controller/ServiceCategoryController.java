package com.jkstack.dsm.service.directory.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkstack.dsm.common.BaseController;
import com.jkstack.dsm.common.MessageException;
import com.jkstack.dsm.common.PageResult;
import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.service.directory.controller.vo.ServiceCategoryTreeVO;
import com.jkstack.dsm.service.directory.controller.vo.ServiceCategoryVO;
import com.jkstack.dsm.service.directory.entity.ServiceCategoryEntity;
import com.jkstack.dsm.service.directory.service.ServiceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public ResponseResult getServiceCategoryList(@RequestBody PageVO pageVO){
        Page<ServiceCategoryEntity> page = serviceCategoryService.page(new Page<>(pageVO.getPageNo(), pageVO.getPageSize()));

        List<ServiceCategoryTreeVO> records = page.getRecords().stream()
                .map(ServiceCategoryTreeVO::new)
                .collect(Collectors.toList());

        return ResponseResult.success(pageVO.getPageSize(), page.getTotal(), records);
    }

    @ApiOperation(value = "新建/修改服务类别列表")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody @Valid ServiceCategoryVO serviceCategoryVO){
        if(Objects.isNull(serviceCategoryVO.getId())){
            serviceCategoryService.save(serviceCategoryVO.toServiceCategoryEntity());
        }else{
            ServiceCategoryEntity serviceCategoryEntity = serviceCategoryService.getById(serviceCategoryVO.getId());
            serviceCategoryEntity.setName(serviceCategoryVO.getName());
            serviceCategoryEntity.setParentId(serviceCategoryVO.getParentId());
            serviceCategoryEntity.setDescription(serviceCategoryVO.getDescription());
            serviceCategoryService.save(serviceCategoryEntity);
        }
        return ResponseResult.success();
    }

    @ApiOperation(value = "批量删除服务类别")
    @PostMapping("/deleteServiceCategory")
    public ResponseResult deleteServiceCategory(@RequestParam List<Long> serviceCategoryIds) throws MessageException {
        if(CollectionUtils.isEmpty(serviceCategoryIds)){
            throw new MessageException("请选择需要删除的服务类别!");
        }

        serviceCategoryService.deleteByIds(serviceCategoryIds);

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
