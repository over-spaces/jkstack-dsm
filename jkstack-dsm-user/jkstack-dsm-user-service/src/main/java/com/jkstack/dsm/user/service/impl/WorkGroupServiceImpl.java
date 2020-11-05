package com.jkstack.dsm.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Maps;
import com.jkstack.dsm.common.Assert;
import com.jkstack.dsm.common.MessageException;
import com.jkstack.dsm.common.service.CommonServiceImpl;
import com.jkstack.dsm.user.controller.vo.WorkGroupUserVO;
import com.jkstack.dsm.user.controller.vo.WorkGroupVO;
import com.jkstack.dsm.user.entity.WorkGroupEntity;
import com.jkstack.dsm.user.entity.WorkGroupUserEntity;
import com.jkstack.dsm.user.mapper.WorkGroupMapper;
import com.jkstack.dsm.user.mapper.WorkGroupUserMapper;
import com.jkstack.dsm.user.service.WorkGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
    @Resource
    private WorkGroupUserMapper workGroupUserMapper;

    /**
     * 查询用户所在工作组列表
     *
     * @param userId 用户ID
     * @return 工作组列表
     */
    @Override
    public List<WorkGroupEntity> selectListByUserId(String userId) {
        return workGroupMapper.selectListByUserId(userId);
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
            List<WorkGroupEntity> workGroupEntities = selectListByUserId(userId);
            result.put(userId, workGroupEntities);
        }
        return result;
    }

    /**
     * 统计工作组用户数量
     *
     * @param workGroupId 工作组ID
     * @return 用户数量
     */
    @Override
    public long selectCountUserByWorkGroupId(String workGroupId) {
        Long count =  workGroupMapper.selectCountUserByWorkGroupId(workGroupId);
        return Optional.ofNullable(count).orElse(0L);
    }

    @Override
    public void deleteByWorkGroupId(String workGroupId) throws MessageException {
        long userCount = selectCountUserByWorkGroupId(workGroupId);
        if(userCount <= 0){
            removeByBusinessId(workGroupId);
        }else{
            throw new MessageException("当前工作组有成员，无法删除");
        }
    }

    @Override
    public void addUser(String workGroupId, Set<String> userIds) throws MessageException {
        WorkGroupEntity workGroupEntity = getByBusinessId(workGroupId);
        Assert.isNull(workGroupEntity, "工作组不存在");

        LambdaQueryWrapper<WorkGroupUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkGroupUserEntity::getWorkGroupId, workGroupId);
        List<WorkGroupUserEntity> workGroupUserEntities = workGroupUserMapper.selectList(wrapper);
        List<String> dbUserIds = workGroupUserEntities.stream().map(WorkGroupUserEntity::getUserId).collect(Collectors.toList());

        userIds.stream()
                .filter(userId -> !dbUserIds.contains(userId))
                .forEach(userId -> {
                    workGroupUserMapper.insert(new WorkGroupUserEntity(userId, workGroupId));
                });
    }

    @Override
    public void removeUser(String workGroupId, Set<String> userIds) throws MessageException {
        WorkGroupEntity workGroupEntity = getByBusinessId(workGroupId);
        Assert.isNull(workGroupEntity, "工作组不存在");

        LambdaUpdateWrapper<WorkGroupUserEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(WorkGroupUserEntity::getWorkGroupId, workGroupId);
        wrapper.in(WorkGroupUserEntity::getUserId, userIds);
        workGroupUserMapper.delete(wrapper);
    }

    @Override
    public int getMaxSortValue() {
        Integer maxSort =  workGroupMapper.getMaxSortValue();
        return Optional.ofNullable(maxSort).orElse(0);
    }

    @Override
    public void updateSort(List<String> workGroupIds) {
        if(CollectionUtils.isEmpty(workGroupIds)) {
            return;
        }

        int sort = 1;
        for (String workGroupId : workGroupIds) {
            LambdaUpdateWrapper<WorkGroupEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(WorkGroupEntity::getSort, sort++);
            wrapper.eq(WorkGroupEntity::getWorkGroupId, workGroupId);
            workGroupMapper.update(null, wrapper);
        }
    }

    @Override
    public WorkGroupEntity getByName(String name) {
        LambdaQueryWrapper<WorkGroupEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkGroupEntity::getName, name);
        return workGroupMapper.selectOne(wrapper);
    }
}