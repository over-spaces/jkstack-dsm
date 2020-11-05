package com.jkstack.dsm.user.service;

import com.jkstack.dsm.common.MessageException;
import com.jkstack.dsm.common.service.CommonService;
import com.jkstack.dsm.user.controller.vo.WorkGroupVO;
import com.jkstack.dsm.user.entity.WorkGroupEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * (WorkGroup)表服务接口
 *
 * @author lifang
 * @since 2020-10-27 11:48:42
 */
public interface WorkGroupService extends CommonService<WorkGroupEntity> {

    /**
     * 查询用户所在工作组列表
     * @param userId 用户ID
     * @return 工作组列表
     */
    List<WorkGroupEntity> selectListByUserId(String userId);

    /**
     * 批量查询用户所在工作组列表
     * @param userIds 用户ID集合
     * @return 工作组列表
     */
    Map<String, List<WorkGroupEntity>> listByUserIds(Collection<String> userIds);

    /**
     * 统计工作组用户数量
     * @param workGroupId 工作组ID
     * @return 用户数量
     */
    long selectCountUserByWorkGroupId(String workGroupId);

    /**
     * 根据工作组ID删除工作组，如工作组下有成员，则不允许删除。
     * @param workGroupId 工作组ID
     * @throws MessageException 不允许删除异常
     */
    void deleteByWorkGroupId(String workGroupId) throws MessageException;

    /**
     * 工作组添加成员
     * @param workGroupId 工作组ID
     * @param userIds 成员ID列表
     * @throws MessageException 异常信息
     */
    void addUser(String workGroupId, Set<String> userIds) throws MessageException;

    /**
     * 工作组移除成员
     * @param workGroupId 工作组ID
     * @param userIds 成员ID列表
     * @throws MessageException 异常信息
     */
    void removeUser(String workGroupId, Set<String> userIds) throws MessageException;

    /**
     * 获取工作组排序号最大值
     * @return 最大排序号
     */
    int getMaxSortValue();

    /**
     * 根据list顺序，更新工作组排序。
     * @param workGroupIds 工作组ID列表
     */
    void updateSort(List<String> workGroupIds);

    /**
     * 通过name查询工作组
     * @param name 名称
     * @return 工作组
     */
    WorkGroupEntity getByName(String name);

    /**
     * 按名称模糊查询
     * @param condition 模糊查询条件
     * @return 工作组集合
     */
    List<WorkGroupEntity> selectListByNameLike(String condition);
}