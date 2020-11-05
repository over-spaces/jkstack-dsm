package com.jkstack.dsm.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jkstack.dsm.user.entity.DepartmentUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lifang
 * @since 2020/10/27
 */
public interface DepartmentUserMapper extends BaseMapper<DepartmentUserEntity> {

    /**
     * 获取部门下全部的用户ID列表
     * @param departmentIds 部门ID例表
     * @return 用户ID列表
     */
    Set<String> selectUserIdsByDepartmentIds(@Param("list") List<String> departmentIds);


    /**
     * 查询部门下用户数量
     * @return key:部门ID，value: 用户数
     */
    List<Map<String, Long>> countDepartmentUserNumber();

    /**
     * 更新排序值
     * @param departmentId 部门ID
     * @param userId 用户ID
     * @param sort 排序
     */
    void updateSort(String departmentId, String userId, int sort);
}
