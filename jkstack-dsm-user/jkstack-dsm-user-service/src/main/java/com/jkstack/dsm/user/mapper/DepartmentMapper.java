package com.jkstack.dsm.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jkstack.dsm.user.controller.vo.DepartmentTreeVO;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * (Department)表数据库访问层
 *
 * @author lifang
 * @since 2020-10-26 09:49:26
 */
public interface DepartmentMapper extends BaseMapper<DepartmentEntity> {

    @Select("SELECT dept.* FROM dsm_department dept " +
            " LEFT JOIN dsm_user_department d ON dept.department_id=d.department_id" +
            " WHERE d.user_id='${userId}'")
    List<DepartmentEntity> listByUserId(String userId);


    /**
     * 查询部门下用户数量
     */
    @Select("SELECT d.department_id, count(d.user_id) AS count FROM dsm_user_department d GROUP BY d.department_id")
    List<Map<String, Long>> listDeptUserNumber();

    @Select("SELECT d.department_id AS count FROM dsm_department d WHERE d.lft>#{lft} AND d.rgt<#{rgt}")
    List<String> selectChildrenDepartmentIds(int lft, int rgt);
}