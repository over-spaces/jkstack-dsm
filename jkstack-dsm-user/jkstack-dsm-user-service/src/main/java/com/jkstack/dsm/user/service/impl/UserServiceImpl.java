package com.jkstack.dsm.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkstack.dsm.common.service.CommonServiceImpl;
import com.jkstack.dsm.common.utils.MD5Util;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.user.config.UserConfigProperties;
import com.jkstack.dsm.user.entity.UserEntity;
import com.jkstack.dsm.user.mapper.UserMapper;
import com.jkstack.dsm.user.service.DepartmentService;
import com.jkstack.dsm.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends CommonServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserConfigProperties userConfigProperties;

    @Override
    public void deleteByUserId(List<String> userIds){
        userIds.stream().forEach(this::removeByBusinessId);
    }

    /**
     * 创建用户，并且初始化密码
     *
     * @param userEntity 用户
     */
    @Override
    public UserEntity createUser(UserEntity userEntity) {
        //初始化密码
        userEntity.setPassword(getUserInitPassword());
        save(userEntity);
        return userEntity;
    }

    /**
     * 创建用户，并且初始化密码
     *
     * @param userEntities 用户列表
     */
    @Override
    public void createUser(List<UserEntity> userEntities) {
        userEntities.stream().forEach(this::createUser);
    }

    /**
     * 获取用户初始化密码
     * @return 加密之后的初始化密码
     */
    @Override
    public String getUserInitPassword() {
        return MD5Util.getMD5DefaultSalt(userConfigProperties.getInitPassword());
    }

    /**
     * 校验用户名是否合法
     * @param userId 用户ID
     * @param loginName 用户名
     */
    @Override
    public boolean checkUserLoginName(String userId, String loginName) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserEntity::getLoginName, loginName);
        UserEntity userEntity = userMapper.selectOne(wrapper);
        if(userEntity == null) {
            //没有重名
            return true;
        }else if(StringUtils.equals(userId, userEntity.getUserId())){
            return true;
        }
        //不合法的用户名
        return false;
    }

    @Override
    public IPage<UserEntity> listDepartmentUsers(String departmentId, PageVO pageVO) {
        IPage<UserEntity> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        return userMapper.listDepartmentUsers(departmentId, page);
    }

    /**
     * 统计指定部门下用户数量
     *
     * @param departmentId 部门ID
     * @return 用户数量
     */
    @Override
    public long countUserByDepartmentId(String departmentId) {
        return departmentService.listChildrenDepartmentIds(departmentId).size();
    }
}
