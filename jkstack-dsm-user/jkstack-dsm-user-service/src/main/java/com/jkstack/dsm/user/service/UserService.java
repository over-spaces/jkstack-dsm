package com.jkstack.dsm.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jkstack.dsm.common.service.CommonService;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.user.entity.UserEntity;

import java.util.List;

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
     * 统计指定部门下用户数量
     * @param departmentId 部门ID
     * @return 用户数量
     */
    long countUserByDepartmentId(String departmentId);
}
