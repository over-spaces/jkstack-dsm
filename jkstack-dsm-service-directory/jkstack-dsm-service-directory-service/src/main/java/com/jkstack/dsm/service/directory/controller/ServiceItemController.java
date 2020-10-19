package com.jkstack.dsm.service.directory.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkstack.dsm.common.BaseController;
import com.jkstack.dsm.common.MessageException;
import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.service.directory.controller.vo.ServiceItemQueryVO;
import com.jkstack.dsm.service.directory.controller.vo.ServiceItemVO;
import com.jkstack.dsm.service.directory.entity.ServiceItemEntity;
import com.jkstack.dsm.service.directory.service.ServiceItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author lifang
 * @since 2020/10/15
 */
@Api(tags = "服务项", value = "服务项管理")
@RestController
@RequestMapping("/service/item")
public class ServiceItemController extends BaseController {

    @Autowired
    private ServiceItemService serviceItemService;

    @ApiOperation(value = "查询服务项列")
    @PostMapping("/getServiceItemList")
    public ResponseResult<ServiceItemVO> getServiceItemList(@RequestBody ServiceItemQueryVO queryVO) {
        QueryWrapper<ServiceItemEntity> wrapper = new QueryWrapper<>();
        wrapper.like("serviceItemType", queryVO.getServiceItemType());
        Page page = serviceItemService.page(new Page(queryVO.getPageNo(), queryVO.getPageSize()), wrapper);
        return ResponseResult.success(queryVO.getPageSize(), page.getTotal(), page.getRecords());
    }

    @ApiOperation(value = "新建/编辑服务项")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody ServiceItemVO queryVO) {
        return ResponseResult.success();
    }

    @ApiOperation(value = "批量删除服务项")
    @PostMapping("/deleteServiceItem")
    public ResponseResult deleteServiceCategory(@RequestParam List<Long> serviceItemIds) throws MessageException {
        if (CollectionUtils.isEmpty(serviceItemIds)) {
            throw new MessageException("请选择需要删除的服务项!");
        }

        serviceItemService.deleteByIds(serviceItemIds);
        return ResponseResult.success();
    }

    @ApiOperation(value = "下载服务项导入模板")
    @GetMapping("/downloadImportTemplate")
    public ResponseResult downloadImportTemplate(HttpServletResponse response) {
        return ResponseResult.success();
    }

    @ApiOperation(value = "导出服务项")
    @GetMapping("/exportToExcel")
    public ResponseResult exportToExcel(HttpServletResponse response) {
        return ResponseResult.success();
    }

    @ApiOperation(value = "导入服务项")
    @PostMapping("/excelImport")
    public ResponseResult excelImport(@RequestParam(value = "file") MultipartFile file) {
        return ResponseResult.success();
    }
}
