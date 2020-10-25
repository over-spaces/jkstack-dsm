package com.jkstack.dsm.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lifang
 * @since 2020/10/15
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class SimpleDataVO implements Serializable {

    @ApiModelProperty(value = "业务ID")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;
}
