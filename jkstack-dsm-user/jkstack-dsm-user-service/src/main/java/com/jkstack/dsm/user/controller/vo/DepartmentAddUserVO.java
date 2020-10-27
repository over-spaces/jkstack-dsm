package com.jkstack.dsm.user.controller.vo;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

/**
 * @author lifang
 * @since 2020/10/27
 */
@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class DepartmentAddUserVO implements Serializable {

    @NotBlank(message = "部门ID不允许为空")
    @ApiModelProperty("部门ID")
    private String departmentId;

    @NotEmpty(message = "请选择用户列表")
    @ApiModelProperty("用户ID列表")
    private Set<String> userIds = Sets.newHashSet();

}
