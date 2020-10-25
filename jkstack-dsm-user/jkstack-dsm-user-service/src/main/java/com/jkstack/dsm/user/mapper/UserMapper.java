package com.jkstack.dsm.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jkstack.dsm.user.entity.UserEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lifang
 * @since 2020-09-30
 */
public interface UserMapper extends BaseMapper<UserEntity> {

//    @Select("select u.* from dsm_user u left join dsm_user_department d on u.user_id=d.department_id where d.department_id=${departmentId}")
    IPage<UserEntity> pageUsersByDepartmentId(IPage<?> page, String departmentId);

}