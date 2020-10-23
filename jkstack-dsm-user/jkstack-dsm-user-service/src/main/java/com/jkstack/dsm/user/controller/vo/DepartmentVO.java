package com.jkstack.dsm.user.controller.vo;

import com.jkstack.dsm.user.entity.DepartmentEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lifang
 * @since 2020/10/23
 */
@Setter
@Getter
@NoArgsConstructor
@ApiModel
public class DepartmentVO implements Serializable {

    @ApiModelProperty(value = "部门ID")
    private String departmentId;

    @NotBlank(message = "部门名称不允许为空")
    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "父部门ID")
    private String parentDepartmentId;

    @ApiModelProperty(value = "父部门名称")
    private String parentDepartmentName;

    @ApiModelProperty(value = "部门主管ID")
    private String leaderUserId;

    @ApiModelProperty(value = "部门主管名称")
    private String leaderUserName;
}
