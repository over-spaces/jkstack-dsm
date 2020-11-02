package com.jkstack.dsm.sheet.controller.minio.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * @program: jkstack-dsm
 * @description: 文件基础信息
 * @author: DengMin
 * @create: 2020-11-02 13:52
 **/
@Builder
@Data
public class FileBaseInfo {

    @ApiModelProperty(value = "文件名称")
    private String name;

    private String bucket;

    private String addr;

    public Boolean isPublic;

    @Tolerate
    public FileBaseInfo() {
    }
}
