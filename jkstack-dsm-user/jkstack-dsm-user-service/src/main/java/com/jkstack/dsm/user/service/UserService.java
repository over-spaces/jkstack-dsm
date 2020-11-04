package com.jkstack.dsm.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jkstack.dsm.common.service.CommonService;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.user.controller.vo.UserSimpleVO;
import com.jkstack.dsm.user.entity.UserEntity;

import java.util.List;
import java.util.Set;

public interface UserService extends CommonService<UserEntity> {

    void deleteByUserId(List<String> userIds);


    /**
     * 创建用户，并且初始化密码
     * @param userEntity
     * @return
     */
    UserEntity createUser(UserEntity userEntity);

    /**
     * 创建用户，并且初始化密码
     * @param userEntities
     */
    void createUser(List<UserEntity> userEntities);

    /**
     * 获取用户初始化密码
     * @return 加密之后的初始化密码
     */
    String getUserInitPassword();

    /**
     * 校验用户名是否合法
     * @param userId 用户ID
     * @param loginName 用户名
     */
    boolean checkUserLoginName(String userId, String loginName);

    /**
     * 分页查询部门下用户列表
     * @param departmentId 部门ID
     * @param pageVO 分页查询信息
     * @return 部门下用户列表
     */
    IPage<UserEntity> listDepartmentUsers(String departmentId, PageVO pageVO);

    /**
     * 获取指定部门下全部的用户列表（包含子部门用户）
     * @param departmentId 部门ID
     * @return 部门下全部用户列表
     */
    List<UserSimpleVO> listAllUserByDepartmentId(String departmentId);

    /**
     * 获取部门下直属用户列表
     * @param departmentId 部门ID
     * @return 部门下直属用户列表
     */
    List<UserSimpleVO> listDirectUserByDepartmentId(String departmentId);

    /**
     * 统计指定部门下用户数量
     * @param departmentId 部门ID
     * @return 用户数量
     */
    long countUserByDepartmentId(String departmentId);

    IPage<UserEntity> pageByNotDepartmentId(String departmentId, PageVO pageVO);

    /**
     * 查收工作组下人员列表
     * @param workGroupId 工作组ID
     * @param pageVO 分页信息
     * @return 用户列表
     */
    IPage<UserEntity> listByWorkGroupId(String workGroupId, PageVO pageVO);

    /**
     * 分页查询用户，并且支持模糊匹配
     * @param pageVO 分页查询VO
     * @return 用户列表
     */
    IPage<UserEntity> pageByLike(PageVO pageVO);

    /**
     * 获取部门负责人列表
     * @param departmentId 部门ID
     * @return 部门负责人列表
     */
    List<UserEntity> listLeaderUserByDepartmentId(String departmentId);

    /**
     * 分页查询指定部门下全部的人员，但需要排除指定工作组下的人员。
     * @param departmentId 部门ID
     * @param workGroupId 工作组ID
     * @param pageVO 分页信息
     */
    IPage<UserSimpleVO> pageByDepartmentIdAndNotWorkGroupId(String departmentId, String workGroupId, PageVO pageVO);

    /**
     * 分页查询指定部门下全部的人员
     * @param departmentId 部门ID
     * @param pageVO 分页信息
     */
    IPage<UserSimpleVO> pageByDepartmentId(String departmentId, PageVO pageVO);

    /**
     * 更新用户，绑定部门及工作组
     * @param userEntity 用户
     * @param departmentIds 部门ID集合
     * @param workGroupIds 工作组ID集合
     */
    void update(UserEntity userEntity, Set<String> departmentIds, Set<String> workGroupIds);
}
