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
    List<WorkGroupEntity> listByUserId(String userId);

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
    long countUsers(String workGroupId);

    void deleteByWorkGroupId(String workGroupId) throws MessageException;

    void addUser(String workGroupId, Set<String> userIds) throws MessageException;

    void removeUser(String workGroupId, Set<String> userIds) throws MessageException;

    int getMaxSortValue();

    void updateSort(List<WorkGroupVO> workGroupList);
}