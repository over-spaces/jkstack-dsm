package com.jkstack.dsm.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.jkstack.dsm.common.DsmAssert;
import com.jkstack.dsm.common.MessageException;
import com.jkstack.dsm.common.service.CommonServiceImpl;
import com.jkstack.dsm.common.vo.TreeHelper;
import com.jkstack.dsm.user.controller.vo.DepartmentTreeVO;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import com.jkstack.dsm.user.entity.UserDepartmentEntity;
import com.jkstack.dsm.user.mapper.DepartmentMapper;
import com.jkstack.dsm.user.mapper.UserDepartmentMapper;
import com.jkstack.dsm.user.service.DepartmentService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * (Department)表服务实现类
 *
 * @author lifang
 * @since 2020-10-26 09:49:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DepartmentServiceImpl extends CommonServiceImpl<DepartmentMapper, DepartmentEntity> implements DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private UserDepartmentMapper userDepartmentMapper;

    @Override
    public List<DepartmentEntity> listByUserId(String userId) {
        return departmentMapper.listByUserId(userId);
    }

    @Override
    public List<DepartmentTreeVO> listDepartmentTreeByDeepLE(int deep) {
        QueryWrapper<DepartmentEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().le(DepartmentEntity::getDeep, deep);
        List<DepartmentEntity> departmentEntities = departmentMapper.selectList(wrapper);
        List<DepartmentTreeVO> list = departmentEntities.stream()
                .map(DepartmentTreeVO::new)
                .collect(Collectors.toList());
        return TreeHelper.toTree(list);
    }

    @Override
    public List<DepartmentEntity> listByParentDepartmentId(String departmentId) {
        QueryWrapper<DepartmentEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(DepartmentEntity::getParentDepartmentId, departmentId);
        return departmentMapper.selectList(wrapper);
    }

    /**
     * 获取指定部门下直属部门数量
     *
     * @param departmentId 部门ID
     * @return 直属部门数量
     */
    @Override
    public long countDepartmentByByDepartmentId(String departmentId) {
        QueryWrapper<DepartmentEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(DepartmentEntity::getParentDepartmentId, departmentId);
        return departmentMapper.selectCount(wrapper);
    }

    /**
     * 查询部门下用户数量
     */
    @Override
    public Map<String, Long> queryDeptUserNumber() {
        List<Map<String, Long>> list = departmentMapper.listDeptUserNumber();
        Map<String, Long> result = Maps.newHashMapWithExpectedSize(list.size());
        list.stream().forEach(map -> {
            result.put(String.valueOf(map.get("department_id")), map.get("count"));
        });
        return result;
    }

    /**
     * 移除部门下用户列表
     *
     * @param departmentId 部门ID
     * @param userIds      用户列表
     */
    @Override
    public void removeUser(String departmentId, Set<String> userIds) {
        DepartmentEntity departmentEntity = getByBusinessId(departmentId);
        if (departmentEntity == null) {
            return;
        }
        boolean contains = userIds.contains(departmentEntity.getLeaderUserId());
        if (contains) {
            departmentEntity.setLeaderUserId(null);
            updateById(departmentEntity);
        }
        QueryWrapper<UserDepartmentEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserDepartmentEntity::getDepartmentId, departmentId)
                .and(w -> w.in(UserDepartmentEntity::getUserId, userIds));
        userDepartmentMapper.delete(wrapper);
    }

    /**
     * 获取指定部门下全部的子部门
     *
     * @param departmentId 部门ID
     * @return 全部子部门
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> listChildrenDepartmentIds(String departmentId) {
        DepartmentEntity departmentEntity = getByBusinessId(departmentId);
        if (departmentEntity == null) {
            return Collections.emptyList();
        }
        return departmentMapper.listChildrenDepartmentIds(departmentEntity.getLft(), departmentEntity.getRgt());
    }

    /**
     * 部门添加用户
     *
     * @param departmentId 部门ID
     * @param userIds      用户ID列表
     */
    @Override
    public void addUser(String departmentId, Set<String> userIds) throws MessageException {

        DepartmentEntity departmentEntity = getByBusinessId(departmentId);
        DsmAssert.isNull(departmentEntity, "部门不存在!");

        QueryWrapper<UserDepartmentEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserDepartmentEntity::getDepartmentId, departmentId);

        //避免重复添加
        List<UserDepartmentEntity> dbUserDepartmentEntities = userDepartmentMapper.selectList(wrapper);
        Set<String> dbUserIds = dbUserDepartmentEntities.stream().map(UserDepartmentEntity::getUserId).collect(Collectors.toSet());
        Collection<String> newUserIds = CollectionUtils.subtract(userIds, dbUserIds);

        //保存
        newUserIds.stream()
                .map(userId -> new UserDepartmentEntity(userId, departmentId))
                .forEach(userDepartmentEntity -> userDepartmentMapper.insert(userDepartmentEntity));
    }

    /**
     * 查询用户所属的部门
     *
     * @param userIds 用户ID列表
     */
    @Override
    public Map<String, List<DepartmentEntity>> listByUsers(List<String> userIds) {
        Map<String, List<DepartmentEntity>> result = Maps.newHashMap();
        for (String userId : userIds) {
            List<DepartmentEntity> departmentEntities = listByUserId(userId);
            result.put(userId, departmentEntities);
        }
        return result;
    }
}