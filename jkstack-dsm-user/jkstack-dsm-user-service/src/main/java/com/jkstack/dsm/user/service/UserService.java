package com.jkstack.dsm.user.service;

import com.jkstack.dsm.user.entity.UserEntity;

public interface UserService {

    UserEntity findById(Long id);

    void save(UserEntity userEntity);

    void updateById(UserEntity userEntity);

}
