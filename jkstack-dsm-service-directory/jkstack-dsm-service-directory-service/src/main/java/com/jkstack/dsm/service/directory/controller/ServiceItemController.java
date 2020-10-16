package com.jkstack.dsm.service.directory.controller;

import com.jkstack.dsm.common.BaseEntity;
import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.service.directory.controller.vo.ServiceItemQueryVO;
import com.jkstack.dsm.service.directory.service.ServiceItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lifang
 * @since 2020/10/15
 */
@Api(tags = "服务项", value = "服务项管理")
@RestController
@RequestMapping("/service/directory")
public class ServiceItemController extends BaseEntity {

    @Autowired
    ServiceItemService serviceItemService;

    @ApiOperation(value = "查询请求、事件、问题、变更列表")
    @PostMapping("/getServiceItemList")
    public ResponseResult getServiceItemList(@RequestBody ServiceItemQueryVO queryVO) {
        return ResponseResult.success();
    }

    @ApiOperation(value = "新建/编辑服务项")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody ServiceItemQueryVO queryVO) {
        return ResponseResult.success();
    }


}
