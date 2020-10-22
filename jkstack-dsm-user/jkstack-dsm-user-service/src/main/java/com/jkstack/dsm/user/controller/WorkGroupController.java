package com.jkstack.dsm.user.controller;

import com.jkstack.dsm.common.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lifang
 * @since 2020/10/21
 */
@Api(tags = "工作组")
@RestController()
@RequestMapping("/work/group")
public class WorkGroupController extends BaseController {
}
