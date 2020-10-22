package com.jkstack.dsm.user.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.jkstack.dsm.common.BaseEntity;
import com.jkstack.dsm.common.annotation.TableBusinessId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 用户类
 * @author lifang
 * @since 2020/10/12
 */
@Setter
@Getter
@Table(name = "dsm_user")
@TableName(value = "dsm_user")
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    /**
     * 用户ID
     */
    @TableBusinessId
    @TableField(fill = FieldFill.INSERT)
    @Column(length = 64, comment = "用户ID")
    @Unique
    @Index("idx_user_id")
    private String userId;

    /**
     * 用户名/登录名
     */
    @Unique
    @Index("idx_login_name")
    @Column(length = 64, comment = "用户名")
    private String loginName;

    /**
     * 姓名
     */
    @Column(length = 64, comment = "姓名")
    private String name;

    /**
     * 工号
     */
    @Column(length = 64, comment = "工号")
    private String userNo;

    /**
     * 密码
     */
    @Column(length = 64, comment = "密码")
    private String password;

    /**
     * 用户状态（在职，休假，离职）
     */
    @EnumValue
    @Column(length = 20, type = MySqlTypeConstant.VARCHAR, comment = "用户状态")
    private UserStatusEnum status;

    /**
     * 电话
     */
    @Column(length = 20, comment = "电话")
    private String phone;

    /**
     * 邮箱
     */
    @Column(length = 64, comment = "邮箱")
    private String email;

    /**
     * 微信
     */
    @Column(length = 64, comment = "微信")
    private String wechat;


    public UserEntity(String loginName, String name, String password){
        this.loginName = loginName;
        this.name = name;
        this.password = password;
    }
}
