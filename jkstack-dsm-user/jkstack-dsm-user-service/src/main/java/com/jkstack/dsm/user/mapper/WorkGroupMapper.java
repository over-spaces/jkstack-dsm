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

    List<WorkGroupEntity> listByUserId(String userId);

}