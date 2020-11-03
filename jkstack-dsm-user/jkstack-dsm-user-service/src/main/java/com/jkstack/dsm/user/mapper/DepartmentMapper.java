package com.jkstack.dsm.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jkstack.dsm.common.lr.LRNode;
import com.jkstack.dsm.user.controller.vo.DepartmentTreeVO;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * (Department)表数据库访问层
 *
 * @author lifang
 * @since 2020-10-26 09:49:26
 */
public interface DepartmentMapper extends BaseMapper<DepartmentEntity> {

    List<DepartmentEntity> listByUserId(String userId);


    /**
     * 查询部门下用户数量
     */
    List<Map<String, Long>> listDeptUserNumber();

    /**
     * 获取子部门列表ID
     * @param lft LR算法左值
     * @param rgt LR算法右值
     * @return
     */
    List<String> listChildrenDepartmentIds(int lft, int rgt);

    /**
     * 获取最大rgt值
     */
    Integer getMaxRightValue();

    List<LRNode> listAllNode();

    Integer getMaxSortValueByDeep(int deep);

    Integer getMaxSortValueByParentDepartmentId(String parentDepartmentId);

    Integer isExistRootDepartment();
}