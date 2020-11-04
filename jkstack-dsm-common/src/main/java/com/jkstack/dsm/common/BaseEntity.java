package com.jkstack.dsm.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 任何实体类都应该继承此类。
 *
 * @author lifang
 * @since 2020-10-01
 */
public abstract class BaseEntity implements Serializable {

    /**
     * 主键ID
     * 注意：此ID不要参与任何业务逻辑，让它安静的自增就好了，处理业务逻辑用具体实体类的ID，如ServiceItemEntity#serviceItemId.
     */
    @TableId(type = IdType.AUTO)
    @Column(name = "id", type = MySqlTypeConstant.BIGINT, isKey = true, isAutoIncrement = true)
    private Long id;

    /**
     * 租户ID
     */
    @Column(length = 64)
    private String tenantId;

    /**
     * 公司ID
     */
    @Column(length = 64)
    private String companyId;


    /**
     * 创建时间
     */
    @Column(type = MySqlTypeConstant.DATETIME)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人ID
     */
    @Column(length = 64)
    private String creatorId;

    /**
     * 修改时间
     */
    @Column(type = MySqlTypeConstant.DATETIME)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

    /**
     * 修改人ID
     */
    @Column(length = 64)
    private String modifierId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseEntity that = (BaseEntity) o;
        if (id == null || that.id == null) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
