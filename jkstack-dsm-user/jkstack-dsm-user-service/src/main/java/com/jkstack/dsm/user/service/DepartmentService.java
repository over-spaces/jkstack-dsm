package com.jkstack.dsm.user.service;

import com.jkstack.dsm.common.MessageException;
import com.jkstack.dsm.common.service.CommonService;
import com.jkstack.dsm.user.controller.vo.DepartmentTreeVO;
import com.jkstack.dsm.user.entity.DepartmentEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * (Department)表服务接口
 *
 * @author lifang
 * @since 2020-10-26 09:49:26
 */
public interface DepartmentService extends CommonService<DepartmentEntity> {

    List<DepartmentEntity> listByUserId(String userId);

    List<DepartmentTreeVO> listDepartmentTreeByDeepLE(int deep);

    /**
     * 获取指定部门下的直属部门列表
     * @param departmentId 部门ID
     * @return 直属部门列表
     */
    List<DepartmentEntity> listByParentDepartmentId(String departmentId);

    /**
     * 获取指定部门下直属部门数量
     * @param departmentId 部门ID
     * @return 直属部门数量
     */
    long countDepartmentByByDepartmentId(String departmentId);

    /**
     * 查询部门下用户数量
     * @return
     */
    Map<String, Long> queryDeptUserNumber();

    /**
     * 移除部门下用户列表
     * @param departmentId 部门ID
     * @param userIds 用户列表
     */
    void removeUser(String departmentId, List<String> userIds);

    /**
     * 获取指定部门下全部的子部门
     * @param departmentId 部门ID
     * @return 全部子部门
     */
    List<String> listChildrenDepartmentIds(String departmentId);

    /**
     * 部门添加用户
     * @param departmentId 部门ID
     * @param userIds 用户ID列表
     */
    void addUser(String departmentId, Set<String> userIds) throws MessageException;
}