package com.jkstack.dsm.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jkstack.dsm.common.*;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.user.controller.vo.WorkGroupAddUserVO;
import com.jkstack.dsm.user.controller.vo.WorkGroupUserVO;
import com.jkstack.dsm.user.controller.vo.WorkGroupVO;
import com.jkstack.dsm.user.entity.UserEntity;
import com.jkstack.dsm.user.entity.WorkGroupEntity;
import com.jkstack.dsm.user.service.UserService;
import com.jkstack.dsm.user.service.WorkGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (WorkGroup)表控制层
 *
 * @author lifang
 * @since 2020-10-27 11:48:42
 */
@Api(tags = "工作组")
@RestController
@RequestMapping("/work-group")
public class WorkGroupController extends BaseController {

    /**
     * 服务对象
     */
    @Autowired
    private WorkGroupService workGroupService;
    @Autowired
    private UserService userService;


    @ApiOperation(value = "新建/修改工作组")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody @Valid WorkGroupVO workGroupVO) throws MessageException {
        checkWorkGroupVO();
        WorkGroupEntity workGroupEntity;
        if (StringUtils.isBlank(workGroupVO.getWorkGroupId())) {
            workGroupEntity = new WorkGroupEntity();
        } else {
            workGroupEntity = workGroupService.getByBusinessId(workGroupVO.getWorkGroupId());
            Assert.isNull(workGroupEntity, "工作组不存在");
        }
        workGroupEntity.setName(workGroupVO.getName());
        workGroupEntity.setDescription(workGroupVO.getDescription());
        workGroupService.saveOrUpdate(workGroupEntity);
        return ResponseResult.success();
    }

    @ApiOperation(value = "获取工作组明细")
    @PostMapping("/get")
    public ResponseResult<WorkGroupVO> get(@RequestParam String workGroupId) throws MessageException {
        WorkGroupEntity workGroupEntity = workGroupService.getByBusinessId(workGroupId);
        Assert.isNull(workGroupEntity, "工作组不存在");
        WorkGroupVO workGroupVO = new WorkGroupVO(workGroupEntity);
        return ResponseResult.success(workGroupVO);
    }

    @ApiOperation(value = "工作组列表")
    @GetMapping("/list")
    public ResponseResult<List<WorkGroupVO>> list() throws MessageException {
        List<WorkGroupEntity> workGroupEntities = workGroupService.list();
        List<WorkGroupVO> list = workGroupEntities.stream()
                .sorted(Comparator.comparing(WorkGroupEntity::getSort))
                .map(WorkGroupVO::new)
                .collect(Collectors.toList());
        return ResponseResult.success(list);
    }

    @ApiOperation(value = "工作组成员列表")
    @PostMapping("/users")
    public ResponseResult<PageResult<WorkGroupUserVO>> users(@RequestBody PageVO pageVO) throws MessageException {
        IPage<UserEntity> page = userService.listByWorkGroupId(pageVO.getId(), pageVO);

        List<WorkGroupUserVO> list = page.getRecords().stream()
                .map(WorkGroupUserVO::new)
                .collect(Collectors.toList());
        return ResponseResult.success(new PageResult(page.getPages(), page.getTotal(), list));
    }

    @ApiOperation(value = "工作组添加成员")
    @PostMapping("/add/user")
    public ResponseResult addUser(@RequestBody WorkGroupAddUserVO workGroupAddUserVO) throws MessageException {
        return ResponseResult.success();
    }

    @ApiOperation(value = "工作组移除成员")
    @PostMapping("/remove/user")
    public ResponseResult removeUser(@RequestBody WorkGroupAddUserVO workGroupAddUserVO) throws MessageException {
        return ResponseResult.success();
    }

    private void checkWorkGroupVO() {

    }

}