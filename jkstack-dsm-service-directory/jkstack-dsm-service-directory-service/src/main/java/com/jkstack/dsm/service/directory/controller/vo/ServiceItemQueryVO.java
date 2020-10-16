package com.jkstack.dsm.service.directory.controller.vo;

import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.service.directory.entity.ServiceItemTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lifang
 * @since 2020/10/15
 */
@Data
@ApiModel
public class ServiceItemQueryVO extends PageVO {

    @ApiModelProperty(value = "服务类别", example = "REQUEST")
    private ServiceItemTypeEnum serviceItemType;

}
