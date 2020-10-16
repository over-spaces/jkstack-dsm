package com.jkstack.dsm.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jkstack.dsm.user.entity.UserEntity;
import com.jkstack.dsm.user.mapper.UserMapper;
import com.jkstack.dsm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity findById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<UserEntity> listUsers(int pageNo, int pageSize) {
        IPage page = new Page(pageNo, pageSize);
        IPage iPage = userMapper.selectPage(page, null);
        return iPage.getRecords();
    }
}
