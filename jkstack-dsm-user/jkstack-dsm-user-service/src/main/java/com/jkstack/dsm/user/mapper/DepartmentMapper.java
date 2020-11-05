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

    /**
     * 查询用户所属部门列表
     * @param userId 用户ID
     * @return 部门列表
     */
    List<DepartmentEntity> selectListByUserId(String userId);

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

    /**
     * 获取LRNode例表
     */
    List<LRNode> selectLRNodeList();

    Integer getMaxSortValueByDeep(int deep);

    Integer getMaxSortValueByParentDepartmentId(String parentDepartmentId);

    /**
     * 判断是否存在非departmentId的其他根节点
     * @param departmentId 部门ID
     * @return 1 存在根节点
     */
    Integer isExistRootDepartmentByNotDepartmentId(String departmentId);

    /**
     * 判断是否存在根节点
     * @return 1 存在根节点
     */
    Integer isExistRootDepartment();
}