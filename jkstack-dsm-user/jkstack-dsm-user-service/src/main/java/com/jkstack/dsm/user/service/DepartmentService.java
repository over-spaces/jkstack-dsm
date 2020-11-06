package com.jkstack.dsm.user.service;

import com.jkstack.dsm.common.MessageException;
import com.jkstack.dsm.common.service.CommonService;
import com.jkstack.dsm.user.controller.vo.DepartmentTreeVO;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import org.apache.commons.lang3.StringUtils;

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

    /**
     * 查询用户关联的全部部门
     * @param userId 用户ID
     * @return 部门列表
     */
    List<DepartmentEntity> selectListByUserId(String userId);

    /**
     * 根据deep查询部门树节点信息
     * @param deep 部门深度
     * @return 部门树列表
     */
    List<DepartmentTreeVO> selectListDepartmentTreeByDeepLE(int deep);

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
     * @return key:部门ID，value:用户数
     */
    Map<String, Long> countDepartmentUserNumber();

    /**
     * 移除部门下用户列表
     * @param departmentId 部门ID
     * @param userIds 用户列表
     */
    void removeUser(String departmentId, Set<String> userIds);

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
     * @exception 异常信息
     */
    void addUser(String departmentId, Set<String> userIds) throws MessageException;

    /**
     * 查询用户所属的部门
     * @param userIds 用户ID列表
     * @return key:userId value:部门列表
     */
    Map<String, List<DepartmentEntity>> selectUserDepartmentMapByUserIds(List<String> userIds);

    /**
     * 按名称模糊查询部门列表
     * @param fullPathName 名称
     * @return 部门列表
     */
    List<DepartmentEntity> selectListByFullPathNameLike(String fullPathName);

    /**
     * 校验部门名称是否合法（重名校验）
     * @param departmentId 部门ID，新建为空
     * @param name 待校验的名称
     * @param parentDepartmentId 父节点ID
     */
    void checkName(String departmentId, String name, String parentDepartmentId) throws MessageException;

    /**
     * 获取相同深度的节点最大排序值
     * @param parentDepartmentId 父部门ID
     */
    int getSameDeepMaxSortValueByParentDepartmentId(String parentDepartmentId);

    /**
     * 获取指定部门的全部兄弟节点（相同父节点）
     * @return departmentId 部门ID
     * @return 兄弟部门列表
     */
    List<DepartmentEntity> selectListSiblingDepartmentByDepartmentId(String departmentId);

    /**
     * 根据部门ID删除部门
     * @param departmentId 部门ID
     * @throws MessageException 删除异常
     */
    void deleteByDepartmentId(String departmentId) throws MessageException;

    /**
     * 更新部门排序
     * @param departmentEntities 有序的部门列表
     */
    void updateDepartmentSort(List<DepartmentEntity> departmentEntities);

    /**
     * 判断是否存在部门根节点
     * @param departmentId 部门ID
     * @return true 存在， false 不存在
     */
    boolean isExistRootDepartment(String departmentId);

    /**
     * 保存/更新部门
     * @param departmentEntity 部门
     * @param leaderUserIds 部门主管ID集合
     */
    void saveDepartment(DepartmentEntity departmentEntity, List<String> leaderUserIds);
}