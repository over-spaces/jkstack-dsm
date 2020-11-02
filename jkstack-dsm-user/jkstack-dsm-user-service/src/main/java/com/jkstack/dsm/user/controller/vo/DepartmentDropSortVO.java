package com.jkstack.dsm.user.controller.vo;

import com.jkstack.dsm.common.vo.SimpleTreeDataVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 部门树拖拽排序
 * @author lifang
 * @since 2020/10/28
 */
@Setter
@Getter
@NoArgsConstructor
@ApiModel(description = "部门树拖拽VO")
public class DepartmentDropSortVO implements Serializable {

    public enum DropType{
        /**
         * 前
         */
        before,

        /**
         * 后
         */
        after
    }

    @ApiModelProperty(value = "拖拽的节点")
    private SimpleTreeDataVO dropNode;

    @ApiModelProperty(value = "拖拽的目标节点")
    private SimpleTreeDataVO targetNode;

    @ApiModelProperty(value = "目标节点位置")
    private DropType dropType;

}
