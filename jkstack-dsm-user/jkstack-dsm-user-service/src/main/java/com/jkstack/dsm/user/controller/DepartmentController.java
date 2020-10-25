package com.jkstack.dsm.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkstack.dsm.common.BaseController;
import com.jkstack.dsm.common.PageResult;
import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.common.vo.SimpleTreeDataVO;
import com.jkstack.dsm.user.controller.vo.DepartmentChildrenListVO;
import com.jkstack.dsm.user.controller.vo.DepartmentUserVO;
import com.jkstack.dsm.user.controller.vo.DepartmentVO;
import com.jkstack.dsm.user.controller.vo.UserVO;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import com.jkstack.dsm.user.entity.UserEntity;
import com.jkstack.dsm.user.service.DepartmentService;
import com.jkstack.dsm.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author lifang
 * @since 2020/10/21
 */
@Api(tags = "部门管理")
@RestController()
@RequestMapping("/department")
public class DepartmentController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;


    @ApiOperation(value = "部门列表")
    @GetMapping("/tree/list")
    public ResponseResult<List<SimpleTreeDataVO>> list(@RequestParam(required = false) String departmentId){
        return ResponseResult.success();
    }

    @ApiOperation(value = "新建/编辑部门")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody @Valid DepartmentVO departmentVO){
        DepartmentEntity departmentEntity;
        if(StringUtils.isBlank(departmentVO.getDepartmentId())){
            //新建
            departmentEntity = new DepartmentEntity();
        }else{
            //更新

        }
        return ResponseResult.success();
    }

    @ApiOperation(value = "获取子部门列表")
    @PostMapping("/children/list")
    public ResponseResult<DepartmentChildrenListVO> listChildren(@RequestParam String departmentId,
                                                                 @RequestBody PageVO pageVO){
        return ResponseResult.success();
    }

    @ApiOperation("部门下用户列表")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功"))
    @PostMapping("/users")
    public ResponseResult<PageResult<DepartmentUserVO>> listDepartmentUsers(@RequestParam String departmentId,
                                                                            @RequestBody PageVO pageVO) {
        IPage<UserEntity> pageUsers = userService.pageUsersByDepartmentId(new Page(pageVO.getPageNo(), pageVO.getPageSize()), departmentId);

        PageResult<DepartmentUserVO> pageResult = new PageResult();
        //人数
        pageResult.getExpand().put("userCount", 0);
        //子部门数量
        pageResult.getExpand().put("deptCount", 0);
        return ResponseResult.success(pageResult);
    }
}
