package com.jkstack.dsm.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jkstack.dsm.user.controller.vo.UserSimpleVO;
import com.jkstack.dsm.user.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lifang
 * @since 2020-09-30
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    @Select("SELECT user.* FROM dsm_user user LEFT JOIN dsm_user_department d ON user.user_id=d.user_id WHERE d.department_id='${departmentId}'")
    IPage<UserEntity> listDepartmentUsers(String departmentId, IPage<UserEntity> page);

    @Select("SELECT * FROM dsm_user WHERE user_id NOT IN (SELECT user_id FROM dsm_user_department WHERE department_id='${departmentId}')")
    IPage<UserEntity> pageByNotDepartmentId(String departmentId, IPage<UserEntity> page);

    IPage<UserEntity> pageByNotDepartmentIdAndLike(String departmentId, String condition, IPage<UserEntity> page);

    List<UserSimpleVO> listAllByDepartmentId(String departmentId);
}