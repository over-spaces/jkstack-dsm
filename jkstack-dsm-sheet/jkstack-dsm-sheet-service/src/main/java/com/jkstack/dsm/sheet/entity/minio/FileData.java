package com.jkstack.dsm.sheet.entity.minio;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.jkstack.dsm.common.BaseEntity;
import com.jkstack.dsm.common.annotation.TableBusinessId;
import com.jkstack.dsm.sheet.enums.FileTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import java.util.Date;

/**
 * @program: jkstack-dsm
 * @description: 资源表
 * @author: DengMin
 * @create: 2020-11-02 11:46
 **/
@Setter
@Getter
@Table(name = "dsm_file_data")
@TableName(value = "dsm_file_data")
@Builder
public class FileData extends BaseEntity {

    @TableBusinessId
    @TableField(fill = FieldFill.INSERT)
    @Column(length = 64, comment = "文件ID")
    @Index(value = "file_id")
    private String fileId;

    @Column(length = 225,type = MySqlTypeConstant.VARCHAR, comment = "文件名称")
    private String fileName;

    @Column(length = 225,type = MySqlTypeConstant.VARCHAR, comment = "oss文件名称")
    private String ossName;

    @Column(length = 500,type = MySqlTypeConstant.VARCHAR,comment = "文件地址")
    private String fileUrl;

    @EnumValue
    @Column(length = 20, type = MySqlTypeConstant.VARCHAR, comment = "文件类型")
    private FileTypeEnum fileType;

    @Column(length = 225,type = MySqlTypeConstant.VARCHAR,comment = "文件夹")
    private String fileFolder;

    @Column(length = 20,type = MySqlTypeConstant.VARCHAR,comment = "文件后缀")
    private String fileSuffix;

    @Tolerate
    public FileData() {
    }
}
