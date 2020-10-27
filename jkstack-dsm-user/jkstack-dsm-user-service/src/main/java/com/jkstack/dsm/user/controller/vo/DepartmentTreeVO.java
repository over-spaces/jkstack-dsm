package com.jkstack.dsm.user.controller.vo;

import com.jkstack.dsm.common.vo.SimpleTreeDataVO;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * @author lifang
 * @since 2020/10/26
 */
@ApiModel
@NoArgsConstructor
public class DepartmentTreeVO extends SimpleTreeDataVO<DepartmentTreeVO> {

    @ApiModelProperty(value = "下属部门数量")
    private int subDeptCount;

    @ApiModelProperty(value = "是否叶子节点")
    private boolean leaf;


    public DepartmentTreeVO(DepartmentEntity departmentEntity){
        this.setId(departmentEntity.getBusinessId());
        this.setName(departmentEntity.getName());
        this.setParentId(departmentEntity.getParentDepartmentId());
        this.setDeep(departmentEntity.getDeep());
        this.setSort(departmentEntity.getSort());

        Integer lft = Optional.ofNullable(departmentEntity.getLft()).orElse(0);
        Integer rgt = Optional.ofNullable(departmentEntity.getRgt()).orElse(0);
        this.subDeptCount = Math.max(0, rgt - lft - 1) / 2;
        this.leaf = this.subDeptCount <= 0;
    }

    public int getSubDeptCount() {
        return subDeptCount;
    }

    public void setSubDeptCount(int subDeptCount) {
        this.subDeptCount = subDeptCount;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    @Override
    public List<DepartmentTreeVO> getChildren() {
        return super.getChildren();
    }

    @Override
    public void setChildren(List<DepartmentTreeVO> children) {
        super.setChildren(children);
    }

}
