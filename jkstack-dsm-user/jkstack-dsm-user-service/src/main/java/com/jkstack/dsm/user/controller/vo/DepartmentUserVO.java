package com.jkstack.dsm.user.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lifang
 * @since 2020/10/23
 */
@Setter
@Getter
@ApiModel
public class DepartmentUserVO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户编号")
    private String userNo;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "职位")
    private String position;

}
