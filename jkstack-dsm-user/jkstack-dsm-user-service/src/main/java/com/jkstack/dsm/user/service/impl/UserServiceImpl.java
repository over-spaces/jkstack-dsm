package com.jkstack.dsm.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkstack.dsm.common.service.CommonServiceImpl;
import com.jkstack.dsm.common.utils.MD5Util;
import com.jkstack.dsm.common.vo.PageVO;
import com.jkstack.dsm.user.config.UserConfigProperties;
import com.jkstack.dsm.user.controller.vo.UserSimpleVO;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import com.jkstack.dsm.user.entity.UserEntity;
import com.jkstack.dsm.user.mapper.UserMapper;
import com.jkstack.dsm.user.service.DepartmentService;
import com.jkstack.dsm.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
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
     * 获取指定部门下全部的用户列表（包含子部门）
     *
     * @param departmentId 部门ID
     * @return 部门下全部用户列表
     */
    @Override
    public List<UserSimpleVO> listAllByDepartmentId(String departmentId) {
        List<String> childrenDepartmentIds = departmentService.listChildrenDepartmentIds(departmentId);
        //包含自己
        childrenDepartmentIds.add(departmentId);
        return userMapper.listByDepartmentIds(childrenDepartmentIds);
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

    @Override
    public IPage<UserEntity> pageByNotDepartmentId(String departmentId, PageVO pageVO) {
        if(StringUtils.isBlank(pageVO.getCondition())){
            return userMapper.pageByNotDepartmentId(departmentId, new Page<>(pageVO.getPageNo(), pageVO.getPageSize()));
        }else{
            return userMapper.pageByNotDepartmentIdAndLike(departmentId, pageVO.getCondition(), new Page<>(pageVO.getPageNo(), pageVO.getPageSize()));
        }
    }

    /**
     * 查收工作组下人员列表
     *
     * @param workGroupId 工作组ID
     * @param pageVO      分页信息
     * @return 用户列表
     */
    @Override
    public IPage<UserEntity> listByWorkGroupId(String workGroupId, PageVO pageVO) {
        return userMapper.listByWorkGroupId(workGroupId, new Page<>(pageVO.getPageNo(), pageVO.getPageSize()));
    }

    /**
     * 分页查询用户，并且支持模糊匹配
     *
     * @param pageVO 分页查询VO
     * @return 用户列表
     */
    @Override
    public IPage<UserEntity> pageByLike(PageVO pageVO) {
        if(StringUtils.isBlank(pageVO.getCondition())){
            return userMapper.pageByLike(new Page<>(pageVO.getPageNo(), pageVO.getPageSize()), pageVO.getCondition());
        }else {
            return userMapper.selectPage(new Page<>(pageVO.getPageNo(), pageVO.getPageSize()), null);
        }
    }

    /**
     * 获取部门负责人列表
     *
     * @param departmentId 部门ID
     * @return 部门负责人列表
     */
    @Override
    public List<UserEntity> listLeaderUserByDepartmentId(String departmentId) {
        return userMapper.listLeaderUserByDepartmentId(departmentId);
    }
}
