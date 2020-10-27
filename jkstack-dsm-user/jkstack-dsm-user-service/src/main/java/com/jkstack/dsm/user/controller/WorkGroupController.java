package com.jkstack.dsm.user.controller;

import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.user.controller.vo.WorkGroupUserVO;
import com.jkstack.dsm.user.service.WorkGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (WorkGroup)表控制层
 *
 * @author lifang
 * @since 2020-10-27 11:48:42
 */
@Api(tags = "工作组")
@RestController
@RequestMapping("/work-group")
public class WorkGroupController {

    /**
     * 服务对象
     */
    @Autowired
    private WorkGroupService workGroupService;


    @ApiOperation(value = "工作组用户列表")
    @PostMapping("/users")
    public ResponseResult<List<WorkGroupUserVO>> list(@RequestBody PageVO pageVO) {
        return ResponseResult.success();
    }

}