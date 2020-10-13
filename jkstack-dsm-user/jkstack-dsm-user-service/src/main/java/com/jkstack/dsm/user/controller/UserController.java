package com.jkstack.dsm.user.controller;

import com.jkstack.dsm.UserControllerFeign;
import com.jkstack.dsm.common.BaseController;
import com.jkstack.dsm.common.PageVO;
import com.jkstack.dsm.common.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lifang
 * @since 2020/10/13
 */
@RestController("/user")
public class UserController extends BaseController implements UserControllerFeign {

    @PostMapping("/create")
    public ResponseResult create(@RequestBody UserVO user){
        return new ResponseResult();
    }

    @PostMapping("/list")
    public ResponseResult list(@RequestBody PageVO page){
        return new ResponseResult();
    }

    @PostMapping("/delete")
    public ResponseResult delete(@RequestBody List<Long> userIds){
        return new ResponseResult();
    }

}
