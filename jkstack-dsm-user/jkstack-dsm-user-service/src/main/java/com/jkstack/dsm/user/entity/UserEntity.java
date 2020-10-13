package com.jkstack.dsm.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jkstack.dsm.common.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * 用户类
 * @author lifang
 * @since 2020/10/12
 */
@Data
@Entity
@Table(name = "t_user")
@TableName(value = "t_user")
public class UserEntity extends BaseEntity {

    /**
     * 用户名/登录名
     */
    @Column(length = 64)
    private String loginName;

    /**
     * 姓名
     */
    @Column(length = 64)
    private String name;

    /**
     * 工号
     */
    @Column(length = 64)
    private String userNo;

    /**
     * 密码
     */
    @Column(length = 64)
    private String password;

    /**
     * 用户状态（在职，休假，离职）
     */
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    private int deep;

    private int rgt;

    private int lft;

    /**
     * 手机
     */
    @Column(length = 20)
    private String phone;

    /**
     * 邮箱
     */
    @Column(length = 120)
    private String email;

    /**
     * 微信
     */
    @Column(length = 20)
    private String weixin;
}
