package com.jkstack.dsm.user.service;

import com.jkstack.dsm.user.mapper.UserMapper;
import com.jkstack.dsm.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity findById(Long id) {
        return userMapper.selectById(id);
    }
}
