package com.jkstack.dsm.user.controller;

import com.jkstack.dsm.UserControllerFeign;
import com.jkstack.dsm.common.BaseController;
import com.jkstack.dsm.common.PageVO;
import com.jkstack.dsm.common.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lifang
 * @since 2020/10/13
 */
@Api(tags = "用户管理")
@RestController("/user")
public class UserController extends BaseController implements UserControllerFeign {

    @ApiOperation("创建用户")
    @PostMapping("/create")
    public ResponseResult create(@RequestBody UserVO user){
        return new ResponseResult();
    }

    @ApiOperation("用户列表")
    @PostMapping("/list")
    public ResponseResult list(@RequestBody PageVO page){
        return new ResponseResult();
    }

    @ApiOperation("移除用户")
    @PostMapping("/delete")
    public ResponseResult delete(@RequestBody List<Long> userIds){
        return new ResponseResult();
    }

}
