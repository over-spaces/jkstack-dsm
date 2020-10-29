package com.jkstack.dsm.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jkstack.dsm.user.controller.vo.UserSimpleVO;
import com.jkstack.dsm.user.entity.UserEntity;

import java.util.List;

/**
 * @author lifang
 * @since 2020-09-30
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    IPage<UserEntity> listDepartmentUsers(String departmentId, IPage<UserEntity> page);

    IPage<UserEntity> pageByNotDepartmentId(String departmentId, IPage<UserEntity> page);

    IPage<UserEntity> pageByNotDepartmentIdAndLike(String departmentId, String condition, IPage<UserEntity> page);

    List<UserSimpleVO> listAllByDepartmentId(String departmentId);
}