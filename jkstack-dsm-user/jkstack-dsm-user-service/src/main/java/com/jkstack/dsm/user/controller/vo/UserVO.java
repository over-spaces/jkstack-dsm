package com.jkstack.dsm.user.controller.vo;

import com.google.common.collect.Lists;
import com.jkstack.dsm.common.vo.SimpleDataVO;
import com.jkstack.dsm.user.entity.UserEntity;
import com.jkstack.dsm.user.entity.UserStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author lifang
 * @since 2020/10/13
 */
@Getter
@Setter
@ApiModel
@NoArgsConstructor
public class UserVO extends UserSimpleVO {

    @ApiModelProperty(value = "工号", example = "JK0001")
    private String userNo;

    @NotBlank(message="电话不能为空")
    @ApiModelProperty(value = "电话", example = "15618751234", required = true)
    private String phone;

    @ApiModelProperty(value = "邮箱", example = "1234@outlook.com")
    @Email(message = "邮箱不合法")
    private String email;

    @ApiModelProperty(value = "微信", example = "1234@outlook.com")
    private String wechat;

    @ApiModelProperty(value = "职位", example = "客户经理")
    @Length(max = 60, message = "职位不合法")
    private String position;

    @ApiModelProperty(value = "用户状态", example = "IN_SERVICE", required = true)
    private UserStatusEnum status;

    @ApiModelProperty(value = "用户状态-名称", example = "在职")
    private String statusText;

    @ApiModelProperty(value = "角色ID", example = "IN_SERVICE", required = true)
    private String roleId;

    @ApiModelProperty(value = "角色名称", example = "IN_SERVICE")
    private String roleName;

    @ApiModelProperty(value = "部门")
    private List<SimpleDataVO> departmentList = Lists.newArrayList();

    // @ApiModelProperty(value = "工作组")
    // private List<SimpleDataVO> workGroupList = Lists.newArrayList();

    @ApiModelProperty(value = "全部的角色权限列表")
    private List<SimpleDataVO> roleList = Lists.newArrayList();


    public UserVO(UserEntity userEntity){
        super(userEntity);
        this.userNo = userEntity.getUserNo();
        this.phone = userEntity.getPhone();
        this.email = userEntity.getEmail();
        this.wechat = userEntity.getWechat();
        this.status = userEntity.getStatus();
        this.statusText = userEntity.getStatus() != null ? userEntity.getStatus().getText() : null;
        this.position = userEntity.getPosition();
        this.roleId = "default_role";
        this.roleName = "默认角色";
        roleList = Lists.newArrayList(new SimpleDataVO("default_role", "默认角色"));
    }

    public UserEntity toUserEntity(UserEntity userEntity){
        userEntity.setLoginName(super.getLoginName());
        userEntity.setName(super.getName());
        userEntity.setUserNo(this.userNo);
        userEntity.setPhone(this.phone);
        userEntity.setEmail(this.email);
        userEntity.setStatus(this.status);
        userEntity.setWechat(this.wechat);
        userEntity.setPosition(this.position);
        return userEntity;
    }
}
