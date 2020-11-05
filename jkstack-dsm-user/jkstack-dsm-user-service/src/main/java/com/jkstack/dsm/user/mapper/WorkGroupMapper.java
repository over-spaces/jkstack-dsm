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

    /**
     * 查询用户关联的工作组
     * @param userId 用户ID
     * @return 工作组例表
     */
    List<WorkGroupEntity> selectListByUserId(String userId);

    /**
     * 统计工作组下成员数
     * @param workGroupId 工作组ID
     * @return 成员数
     */
    Long selectCountUserByWorkGroupId(String workGroupId);

    /**
     * 获取最大排序号
     * @return 最大排序号
     */
    Integer getMaxSortValue();

}