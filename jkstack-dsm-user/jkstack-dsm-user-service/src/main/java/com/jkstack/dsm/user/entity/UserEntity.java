package com.jkstack.dsm.user.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jkstack.dsm.common.BaseEntity;


/**
 * 用户类
 * @author lifang
 * @since 2020/10/12
 */
@TableName(value = "dsm_user")
public class UserEntity extends BaseEntity {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名/登录名
     */
    private String loginName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 工号
     */
    private String userNo;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户状态（在职，休假，离职）
     */
    @EnumValue
    private UserStatusEnum status;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 微信
     */
    private String weixin;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UserStatusEnum status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }
}
