package com.jkstack.dsm.service.directory.controller.vo;

import com.jkstack.dsm.common.vo.SimpleTreeDataVO;
import com.jkstack.dsm.service.directory.entity.ServiceCategoryEntity;
import lombok.ToString;

import java.util.List;

/**
 * @author lifang
 * @since 2020/10/15
 */
@ToString
public class ServiceCategoryTreeVO extends SimpleTreeDataVO<ServiceCategoryTreeVO> {

    private String description;


    public ServiceCategoryTreeVO(ServiceCategoryEntity serviceCategoryEntity){
        super.setId(serviceCategoryEntity.getServiceCategoryId());
        super.setName(serviceCategoryEntity.getName());
        this.description = serviceCategoryEntity.getDescription();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setChildren(List<ServiceCategoryTreeVO> children) {
        super.setChildren(children);
    }

    @Override
    public List<ServiceCategoryTreeVO> getChildren() {
        return super.getChildren();
    }
}
