package com.jkstack.dsm.user.controller.vo;

import com.jkstack.dsm.user.entity.WorkGroupEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lifang
 * @since 2020/10/30
 */
@Setter
@Getter
@NoArgsConstructor
@ApiModel(description = "工作组VO")
public class WorkGroupVO implements Serializable {

    @ApiModelProperty(value = "工作组ID")
    private String workGroupId;

    @NotBlank(message = "工作组名称不允许为空")
    @ApiModelProperty(value = "工作组名称")
    @Length(max = 60, message = "工作组名称不合法")
    private String name;

    @ApiModelProperty(value = "描述")
    @Length(max = 100, message = "描述内容过长")
    private String description;

    @ApiModelProperty(value = "工作组成员数")
    private Long userCount;

    public WorkGroupVO(WorkGroupEntity workGroupEntity){
        this.workGroupId = workGroupEntity.getWorkGroupId();
        this.name = workGroupEntity.getName();
        this.description = workGroupEntity.getDescription();
    }
}
