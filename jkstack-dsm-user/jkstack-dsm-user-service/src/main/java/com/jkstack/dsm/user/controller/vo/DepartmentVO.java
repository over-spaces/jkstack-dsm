package com.jkstack.dsm.user.controller.vo;

import com.jkstack.dsm.user.entity.DepartmentEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

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

    private List<String> leaderUserIds;

    @ApiModelProperty(value = "部门名称路径")
    private String fullPathName;

    @ApiModelProperty(value = "父部门ID")
    private String parentDepartmentId;

    @ApiModelProperty(value = "原始的父部门ID，前端使用")
    private String originalParentDepartmentId;

    @ApiModelProperty(value = "父部门名称")
    private String parentDepartmentName;

    @ApiModelProperty(value = "上级部门名称路径")
    private String parentFullPathName;

    @ApiModelProperty(value = "是否叶子节点，判断是否允许删除")
    private boolean leaf;

    public DepartmentVO(DepartmentEntity entity) {
        this.departmentId = entity.getDepartmentId();
        this.parentDepartmentId = entity.getParentDepartmentId();
        this.originalParentDepartmentId = entity.getParentDepartmentId();
        this.name = entity.getName();
        this.fullPathName = entity.getFullPathName();
        Integer lft = Optional.ofNullable(entity.getLft()).orElse(0);
        Integer rgt = Optional.ofNullable(entity.getRgt()).orElse(0);
        this.leaf = Math.max(0, rgt - lft - 1) / 2 <= 0;
    }
}
