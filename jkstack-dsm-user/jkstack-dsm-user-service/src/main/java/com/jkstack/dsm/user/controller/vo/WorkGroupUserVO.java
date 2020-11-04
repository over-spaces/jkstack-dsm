package com.jkstack.dsm.user.controller.vo;

import com.jkstack.dsm.common.vo.SimpleDataVO;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import com.jkstack.dsm.user.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lifang
 * @since 2020/10/27
 */
@Setter
@Getter
@ApiModel
@NoArgsConstructor
public class WorkGroupUserVO implements Serializable {

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

    @ApiModelProperty(value = "部门")
    private List<SimpleDataVO> departmentList;

    @ApiModelProperty(value = "工作组范围")
    private List<SimpleDataVO> workGroupScopeList;

    public WorkGroupUserVO(UserEntity userEntity, List<DepartmentEntity> departmentEntities) {
        this.userId = userEntity.getUserId();
        this.loginName = userEntity.getLoginName();
        this.name = userEntity.getName();
        this.userNo = userEntity.getUserNo();
        this.phone = userEntity.getPhone();
        this.departmentList = Optional.ofNullable(departmentEntities).orElse(Collections.emptyList()).stream()
                .map(departmentEntity -> new SimpleDataVO(departmentEntity.getDepartmentId(), departmentEntity.getFullPathName()))
                .collect(Collectors.toList());
    }
}
