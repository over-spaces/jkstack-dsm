package com.jkstack.dsm.user.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lifang
 * @since 2020/10/13
 */
@ApiModel
@Data
public class UserVO implements Serializable {

    @ApiModelProperty(value = "用户名", example = "lisi")
    private String loginName;

}
