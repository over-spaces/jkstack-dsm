package com.jkstack.dsm.user.controller.vo;

import com.jkstack.dsm.common.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lifang
 * @since 2020/11/3
 */
@Data
@ApiModel
public class UserSelectVO extends PageVO {

    public enum TypeEnum{
        /**
         * 所有
         */
        ALL,

        /**
         * 工作组
         */
        WORK_GROUP,

        /**
         * 权限组
         */
        PERM_GROUP,
    }

    @ApiModelProperty("部门ID")
    private String departmentId;

    @ApiModelProperty("选择器类型")
    private TypeEnum type;

}
