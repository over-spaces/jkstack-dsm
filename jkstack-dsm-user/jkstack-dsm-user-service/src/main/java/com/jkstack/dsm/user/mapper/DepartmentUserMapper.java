package com.jkstack.dsm.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jkstack.dsm.user.entity.DepartmentUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author lifang
 * @since 2020/10/27
 */
public interface DepartmentUserMapper extends BaseMapper<DepartmentUserEntity> {

    Set<String> selectUserIdsByDepartmentIds(@Param("list") List<String> departmentIds);

    /**
     * 更新排序值
     * @param departmentId 部门ID
     * @param userId 用户ID
     * @param sort 排序
     */
    void updateSort(String departmentId, String userId, int sort);
}
