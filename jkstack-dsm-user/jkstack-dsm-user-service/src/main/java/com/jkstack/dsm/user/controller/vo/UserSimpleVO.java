package com.jkstack.dsm.user.controller.vo;

import com.jkstack.dsm.user.entity.UserEntity;
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
 * @since 2020/10/27
 */
@Getter
@Setter
@ApiModel
@NoArgsConstructor
public class UserSimpleVO implements Serializable {

    @ApiModelProperty(value = "用户ID", example = "1000")
    private String userId;

    @NotBlank(message="用户名不能为空")
    @Length(max = 60, message = "用户名不合法")
    @ApiModelProperty(value = "用户名", example = "lisi", required = true)
    private String loginName;

    @NotBlank(message="姓名不能为空")
    @Length(max = 60, message = "用户名不合法")
    @ApiModelProperty(value = "姓名", example = "李四", required = true)
    private String name;

    public UserSimpleVO(UserEntity userEntity){
        this.userId = userEntity.getUserId();
        this.loginName = userEntity.getLoginName();
        this.name = userEntity.getName();
    }
}
