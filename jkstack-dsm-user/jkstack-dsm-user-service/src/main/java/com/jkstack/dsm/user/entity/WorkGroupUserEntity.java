package com.jkstack.dsm.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.jkstack.dsm.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户所属工作组
 * @author lifang
 * @since 2020/10/27
 */
@Setter
@Getter
@NoArgsConstructor
@Table(value = "dsm_work_group_user")
@TableName(value = "dsm_work_group_user")
public class WorkGroupUserEntity extends BaseEntity {

    @Column(length = 64, comment = "用户ID")
    @Index("idx_user_id")
    private String userId;

    @Column(length = 64, comment = "工作组ID")
    @Index("idx_work_group_id")
    private String workGroupId;

    public WorkGroupUserEntity(String userId, String workGroupId) {
        this.userId = userId;
        this.workGroupId = workGroupId;
    }
}
