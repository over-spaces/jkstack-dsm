package com.jkstack.dsm.user.service.impl;

import com.google.common.collect.Maps;
import com.jkstack.dsm.common.service.CommonServiceImpl;
import com.jkstack.dsm.user.entity.WorkGroupEntity;
import com.jkstack.dsm.user.mapper.WorkGroupMapper;
import com.jkstack.dsm.user.service.WorkGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * (WorkGroup)表服务实现类
 *
 * @author lifang
 * @since 2020-10-27 11:48:42
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WorkGroupServiceImpl extends CommonServiceImpl<WorkGroupMapper, WorkGroupEntity> implements WorkGroupService {

    @Resource
    private WorkGroupMapper workGroupMapper;

    /**
     * 查询用户所在工作组列表
     *
     * @param userId 用户ID
     * @return 工作组列表
     */
    @Override
    public List<WorkGroupEntity> listByUserId(String userId) {
        return workGroupMapper.listByUserId(userId);
    }

    /**
     * 批量查询用户所在工作组列表
     *
     * @param userIds 用户ID集合
     * @return 工作组列表
     */
    @Override
    public Map<String, List<WorkGroupEntity>> listByUserIds(Collection<String> userIds) {
        if(CollectionUtils.isEmpty(userIds)){
            return Collections.emptyMap();
        }
        Map<String, List<WorkGroupEntity>> result = Maps.newHashMapWithExpectedSize(userIds.size());
        for (String userId : userIds) {
            List<WorkGroupEntity> workGroupEntities = listByUserId(userId);
            result.put(userId, workGroupEntities);
        }
        return result;
    }
}