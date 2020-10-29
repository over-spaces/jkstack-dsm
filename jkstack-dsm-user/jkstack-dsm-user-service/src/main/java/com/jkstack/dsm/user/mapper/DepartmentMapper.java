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

    List<DepartmentEntity> listByUserId(String userId);


    /**
     * 查询部门下用户数量
     */
    List<Map<String, Long>> listDeptUserNumber();

    List<String> listChildrenDepartmentIds(int lft, int rgt);
}