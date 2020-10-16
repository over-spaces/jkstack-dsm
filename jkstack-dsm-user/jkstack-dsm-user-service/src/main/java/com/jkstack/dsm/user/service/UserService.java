package com.jkstack.dsm.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jkstack.dsm.user.entity.UserEntity;

import java.util.List;

public interface UserService extends IService<UserEntity> {

    UserEntity findById(Long id);

    List<UserEntity> listUsers(int pageNo, int pageSize);
}
