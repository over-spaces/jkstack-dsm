package com.jkstack.dsm.system.controller;

import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.system.service.HolidayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Holiday)表控制层
 *
 * @author lifang
 * @since 2020-10-19 18:48:26
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


    @ApiOperation(value = "查询XXXX列表")
    @PostMapping("/list")
    public ResponseResult list(@RequestBody PageVO pageVO) {
        return ResponseResult.success();
    }

}