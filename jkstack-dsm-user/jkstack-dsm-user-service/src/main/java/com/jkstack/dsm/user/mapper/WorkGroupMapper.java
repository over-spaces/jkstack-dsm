package com.jkstack.dsm.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jkstack.dsm.user.entity.WorkGroupEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (WorkGroup)表数据库访问层
 *
 * @author lifang
 * @since 2020-10-27 11:48:42
 */
public interface WorkGroupMapper extends BaseMapper<WorkGroupEntity> {


    @Select("SELECT w.* FROM dsm_work_group w LEFT JOIN dsm_user_work_group u ON w.work_group_id=u.work_group_id WHERE u.user_id=${userId}")
    List<WorkGroupEntity> listByUserId(String userId);
}