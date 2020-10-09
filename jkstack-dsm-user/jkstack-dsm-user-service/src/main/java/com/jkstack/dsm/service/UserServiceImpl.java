package com.jkstack.dsm.service;

import com.jkstack.dsm.dao.UserDao;
import com.jkstack.dsm.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity findById(Long id) {
        return userDao.selectByPrimaryKey(id);
    }
}
