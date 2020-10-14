package com.jkstack.dsm.user.service;

import com.jkstack.dsm.user.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity findById(Long id);

    void save(UserEntity userEntity);

    void updateById(UserEntity userEntity);

    List<UserEntity> listUsers(int pageNo, int pageSize);
}
