package com.jkstack.dsm.user.controller.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.jkstack.dsm.user.entity.UserEntity;
import com.jkstack.dsm.user.entity.UserStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author lifang
 * @since 2020/10/22
 */
@Setter
@Getter
@NoArgsConstructor
public class UserExcelVO implements Serializable {

    @Excel(name = "*用户名", width = 30, isImportField = "true")
    private String loginName;

    @Excel(name = "*姓名", width = 30, isImportField = "true")
    private String name;

    @Excel(name = "*电话", width = 30, isImportField = "true")
    private String phone;

    @Excel(name = "邮箱", width = 30, isImportField = "true")
    private String email;

    @Excel(name = "微信", width = 30, isImportField = "true")
    private String wechat;

    @Excel(name = "工作组", width = 30, isImportField = "true")
    private String workGroupName;

    @Excel(name = "*状态（在职、离职、休假）", width = 30, isImportField = "true")
    private String statusText;

    public UserEntity toUserEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setLoginName(this.loginName);
        userEntity.setName(this.name);
        userEntity.setPhone(this.phone);
        userEntity.setEmail(this.email);
        userEntity.setWechat(this.wechat);
        UserStatusEnum status = Arrays.stream(UserStatusEnum.values())
                .filter(statusEnum -> StringUtils.equals(statusEnum.getText(), this.statusText))
                .findFirst()
                .orElse(null);
        userEntity.setStatus(status);
        return userEntity;
    }
}
