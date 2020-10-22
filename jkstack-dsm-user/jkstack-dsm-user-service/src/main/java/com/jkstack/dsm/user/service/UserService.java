package com.jkstack.dsm.user.service;

import com.jkstack.dsm.common.service.CommonService;
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
}
