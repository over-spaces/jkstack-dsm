package com.jkstack.dsm.user.controller.vo;

import com.jkstack.dsm.common.vo.SimpleDataVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lifang
 * @since 2020/10/23
 */
@Setter
@Getter
@NoArgsConstructor
@ApiModel
public class DepartmentChildrenListVO extends SimpleDataVO {

    @ApiModelProperty(value = "部门人数")
    private int number;

}
