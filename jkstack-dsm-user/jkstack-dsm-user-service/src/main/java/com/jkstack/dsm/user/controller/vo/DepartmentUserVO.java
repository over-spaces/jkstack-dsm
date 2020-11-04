package com.jkstack.dsm.user.controller.vo;

import com.google.common.collect.Lists;
import com.jkstack.dsm.user.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lifang
 * @since 2020/10/23
 */
@Setter
@Getter
@ApiModel
public class DepartmentUserVO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户名称")
    private String loginName;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "用户编号")
    private String userNo;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "职位")
    private List<String> positionList;

    @ApiModelProperty(value = "是否部门主管")
    private boolean leader;


    public DepartmentUserVO(UserEntity userEntity, boolean isLeaderUser) {
        this.userId = userEntity.getUserId();
        this.loginName = userEntity.getLoginName();
        this.name = userEntity.getName();
        this.userNo = userEntity.getUserNo();
        this.phone = userEntity.getPhone();
        this.leader = isLeaderUser;

        this.positionList = new ArrayList<>();
        if(isLeaderUser){
            this.positionList.add("部门主管");
        }
        if(StringUtils.isNotBlank(userEntity.getPosition())){
            this.positionList.add(userEntity.getPosition());
        }
    }
}
