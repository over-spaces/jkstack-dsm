package com.jkstack.dsm.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Maps;
import com.jkstack.dsm.common.Assert;
import com.jkstack.dsm.common.MessageException;
import com.jkstack.dsm.common.service.CommonServiceImpl;
import com.jkstack.dsm.common.vo.TreeHelper;
import com.jkstack.dsm.user.controller.vo.DepartmentTreeVO;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import com.jkstack.dsm.user.entity.DepartmentLeaderEntity;
import com.jkstack.dsm.user.entity.DepartmentUserEntity;
import com.jkstack.dsm.user.mapper.DepartmentLeaderMapper;
import com.jkstack.dsm.user.mapper.DepartmentMapper;
import com.jkstack.dsm.user.mapper.DepartmentUserMapper;
import com.jkstack.dsm.user.service.DepartmentService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
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
    private DepartmentUserMapper departmentUserMapper;
    @Resource
    private DepartmentLeaderMapper departmentLeaderMapper;


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
    public List<DepartmentEntity> listByParentDepartmentId(String parentDepartmentId) {
        LambdaQueryWrapper<DepartmentEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(DepartmentEntity::getSort);
        if (StringUtils.isBlank(parentDepartmentId)) {
            wrapper.isNull(DepartmentEntity::getParentDepartmentId);
        } else {
            wrapper.eq(DepartmentEntity::getParentDepartmentId, parentDepartmentId);
        }
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

        //移除部门主管
        LambdaUpdateWrapper<DepartmentLeaderEntity> departmentLeaderWrapper = new LambdaUpdateWrapper<>();
        departmentLeaderWrapper.eq(DepartmentLeaderEntity::getDepartmentId, departmentId)
                .and(w -> w.in(DepartmentLeaderEntity::getUserId, userIds));
        departmentLeaderMapper.delete(departmentLeaderWrapper);

        //移除部门下成员
        LambdaUpdateWrapper<DepartmentUserEntity> departmentUserWrapper = new LambdaUpdateWrapper<>();
        departmentUserWrapper.eq(DepartmentUserEntity::getDepartmentId, departmentId)
                .and(w -> w.in(DepartmentUserEntity::getUserId, userIds));
        departmentUserMapper.delete(departmentUserWrapper);
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
        Assert.isNull(departmentEntity, "部门不存在!");

        QueryWrapper<DepartmentUserEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(DepartmentUserEntity::getDepartmentId, departmentId);

        //避免重复添加
        List<DepartmentUserEntity> dbUserDepartmentEntities = departmentUserMapper.selectList(wrapper);
        Set<String> dbUserIds = dbUserDepartmentEntities.stream().map(DepartmentUserEntity::getUserId).collect(Collectors.toSet());
        Collection<String> newUserIds = CollectionUtils.subtract(userIds, dbUserIds);

        //保存
        newUserIds.stream()
                .map(userId -> new DepartmentUserEntity(userId, departmentId))
                .forEach(departmentUserEntity -> departmentUserMapper.insert(departmentUserEntity));
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

    /**
     * 按名称模糊查询部门列表
     *
     * @param name 名称
     */
    @Override
    public List<DepartmentEntity> listByNameLike(String name) {
        QueryWrapper<DepartmentEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(DepartmentEntity::getName, name);
        return departmentMapper.selectList(wrapper);
    }

    /**
     * 校验部门名称是否合法（重名校验）
     *
     * @param deep         深度，相同深度的节点校验
     * @param departmentId 部门ID，新建为空
     * @param name         待校验的名称
     */
    @Override
    public void checkName(int deep, String departmentId, String name) throws MessageException {
        QueryWrapper<DepartmentEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(DepartmentEntity::getDeep, deep)
                .and(w -> w.in(DepartmentEntity::getName, name));
        List<DepartmentEntity> departmentEntities = departmentMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(departmentEntities)) {
            return;
        }
        if (StringUtils.isNotBlank(departmentId)) {
            boolean flag = departmentEntities.stream().noneMatch(entity -> departmentId.equals(entity.getDepartmentId()));
            if (flag) {
                throw new MessageException("部门名称已经存在");
            }
        } else {
            throw new MessageException("部门名称已经存在");
        }
    }

    /**
     * 获取相同深度的节点最大排序值
     *
     * @param parentDepartmentId 父部门ID
     */
    @Override
    public int getSameDeepMaxSortValueByParentDepartmentId(String parentDepartmentId) {
        Integer sort;
        if (StringUtils.isBlank(parentDepartmentId)) {
            sort = departmentMapper.getMaxSortValueByDeep(1);
        } else {
            sort = departmentMapper.getMaxSortValueByParentDepartmentId(parentDepartmentId);
        }
        return Optional.ofNullable(sort).orElse(0);
    }

    /**
     * 获取指定部门的全部兄弟节点（相同父节点）
     *
     * @param departmentId
     * @return 兄弟部门列表
     */
    @Override
    public List<DepartmentEntity> listSiblingDepartmentByDepartmentId(String departmentId) {
        DepartmentEntity departmentEntity = getByBusinessId(departmentId);
        if (departmentEntity == null) {
            return Collections.emptyList();
        }
        return listByParentDepartmentId(departmentEntity.getParentDepartmentId());
    }

    @Override
    public void deleteByDepartmentId(String departmentId) throws MessageException {
        DepartmentEntity departmentEntity = getByBusinessId(departmentId);
        Assert.isNull(departmentEntity, "未知的部门");
        List<String> children = departmentMapper.listChildrenDepartmentIds(departmentEntity.getLft(), departmentEntity.getRgt());
        List<String> departmentIds = Lists.newArrayList();
        departmentIds.add(departmentId);
        departmentIds.addAll(children);

        UpdateWrapper<DepartmentUserEntity> wrapper = new UpdateWrapper();
        wrapper.lambda().set(DepartmentUserEntity::getDepartmentId, departmentIds);
        //删除用户关联的部门
        departmentUserMapper.delete(wrapper);

        //删除部门
        removeByBusinessIds(departmentIds);
    }

    @Override
    public void updateDepartmentSort(List<DepartmentEntity> departmentEntities) {
        int sort = 1;
        for (DepartmentEntity departmentEntity : departmentEntities) {
            LambdaUpdateWrapper<DepartmentEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(DepartmentEntity::getDepartmentId,  departmentEntity.getDepartmentId());
            wrapper.set(DepartmentEntity::getSort, sort++);
            departmentMapper.update(null, wrapper);
        }
    }

    /**
     * 判断是否存在部门根节点
     *
     * @return true 存在， false 不存在
     */
    @Override
    public boolean isExistRootDepartment() {
        Integer flag = departmentMapper.isExistRootDepartment();
        return Optional.ofNullable(flag).orElse(0) == 1;
    }

    /**
     * 保存/更新部门
     *
     * @param departmentEntity 部门
     * @param leaderUserIds    部门主管ID集合
     */
    @Override
    public void saveDepartment(DepartmentEntity departmentEntity, List<String> leaderUserIds) {
        if(StringUtils.isNotBlank(departmentEntity.getDepartmentId())){

            if(CollectionUtils.isEmpty(leaderUserIds)) {

                departmentLeaderMapper.deleteByDepartmentId(departmentEntity.getDepartmentId());

            }else{
                List<DepartmentLeaderEntity> departmentLeaderEntities = departmentLeaderMapper.listByDepartmentId(departmentEntity.getDepartmentId());
                List<String> dbLeaderUserIds = departmentLeaderEntities.stream().map(DepartmentLeaderEntity::getUserId).collect(Collectors.toList());

                //删除
                departmentLeaderEntities.stream()
                        .filter(entity -> !leaderUserIds.contains(entity.getUserId()))
                        .forEach(entity -> departmentLeaderMapper.deleteById(entity.getId()));

                //新增
                leaderUserIds.stream()
                        .filter(userId -> !dbLeaderUserIds.contains(userId))
                        .forEach(userId -> {
                            departmentLeaderMapper.insert(new DepartmentLeaderEntity(userId, departmentEntity.getDepartmentId()));
                        });
            }

        }
        saveOrUpdate(departmentEntity);
    }
}