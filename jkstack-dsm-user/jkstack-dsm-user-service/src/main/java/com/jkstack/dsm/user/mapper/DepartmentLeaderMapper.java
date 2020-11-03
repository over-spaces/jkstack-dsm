package com.jkstack.dsm.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jkstack.dsm.user.entity.DepartmentLeaderEntity;

import java.util.List;

/**
 * @author lifang
 * @since 2020/11/3
 */
public interface DepartmentLeaderMapper extends BaseMapper<DepartmentLeaderEntity> {

    List<DepartmentLeaderEntity> listByDepartmentId(String departmentId);

    void deleteByDepartmentId(String departmentId);
}
