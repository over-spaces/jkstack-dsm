package com.jkstack.dsm.service.directory.controller;

import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.service.directory.service.HolidayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (Holiday)表控制层
 *
 * @author lifang
 * @since 2020-10-19 19:35:29
 */
@Api(tags = "接口文档分组")
@RestController
@RequestMapping("/holiday")
public class HolidayController {

    /**
     * 服务对象
     */
    @Autowired
    private HolidayService holidayService;


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "PageVO", name = "pageVO"),
    })
    @ApiOperation(value = "查询XXXX列表")
    @PostMapping("/list")
    public ResponseResult list(@RequestBody PageVO pageVO) {
        return ResponseResult.success();
    }

    @ApiOperation(value = "保存/修改XXXX")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody PageVO pageVO) {
        return ResponseResult.success();
    }

    @ApiOperation(value = "删除XXXX")
    @PostMapping("/delete")
    public ResponseResult delete(@RequestBody List<String> ids) {
        return ResponseResult.success();
    }
}