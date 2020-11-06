package com.jkstack.dsm.admin.entity;

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
 * 网关日志
 * @author lifang
 * @since 2020/11/6
 */
@Setter
@Getter
@NoArgsConstructor
@Table(name = "dsm_user")
@TableName(value = "dsm_user")
public class GatewayLoggerEntity extends BaseEntity {

    @TableBusinessId(prefix = "log")
    @TableField(fill = FieldFill.INSERT)
    @Column(length = 64, comment = "用户ID")
    @Unique
    @Index("idx_gateway_logger_id")
    private String gatewayLoggerId;

    @Column(length = 64)
    private String host;

    @Column(length = 200, comment = "客户端访问地址")
    private String url;

    @Column(type = MySqlTypeConstant.TEXT)
    private String body;
}
