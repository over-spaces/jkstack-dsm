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
import com.jkstack.dsm.user.service.UserService;
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
    @Resource
    private UserService userService;

    /**
     * 查询用户关联的全部部门
     * @param userId 用户ID
     * @return 部门列表
     */
    @Override
    public List<DepartmentEntity> selectListByUserId(String userId) {
        return departmentMapper.selectListByUserId(userId);
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
     * @return key:部门ID，value:用户数
     */
    @Override
    public Map<String, Long> countDepartmentUserNumber() {
        List<Map<String, Long>> departmentUserNumberList = departmentUserMapper.countDepartmentUserNumber();
        Map<String, Long> countDepartmentUserNumberMap = new HashMap<>(departmentUserNumberList.size());
        for (Map<String, Long> map : departmentUserNumberList) {
            countDepartmentUserNumberMap.put(String.valueOf(map.get("departmentId")), map.get("count"));
        }
        return countDepartmentUserNumberMap;
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
            List<DepartmentEntity> departmentEntities = selectListByUserId(userId);
            result.put(userId, departmentEntities);
        }
        return result;
    }

    /**
     * 按名称模糊查询部门列表
     *
     * @param fullName 名称
     */
    @Override
    public List<DepartmentEntity> listByFullNameLike(String fullName) {
        QueryWrapper<DepartmentEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(DepartmentEntity::getFullPathName, fullName);
        return departmentMapper.selectList(wrapper);
    }

    /**
     * 校验部门名称是否合法（重名校验）
     *
     * @param departmentId 部门ID，新建为空
     * @param parentDepartmentId 父部门ID
     * @param name         待校验的名称
     */
    @Override
    public void checkName(String departmentId, String name, String parentDepartmentId) throws MessageException {

        List<DepartmentEntity> departmentEntities;
        if(StringUtils.isNotBlank(parentDepartmentId)){
            departmentEntities = listByParentDepartmentId(parentDepartmentId);
        }else{
            LambdaQueryWrapper<DepartmentEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(DepartmentEntity::getDeep, 1);
            departmentEntities = departmentMapper.selectList(wrapper);
        }

        departmentEntities = departmentEntities.stream()
                .filter(entity -> StringUtils.equals(name, entity.getName()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(departmentEntities)) {
            return;
        }

        if (StringUtils.isNotBlank(departmentId)) {
            boolean flag = departmentEntities.stream()
                    .noneMatch(entity -> departmentId.equals(entity.getDepartmentId()));
            if (flag) {
                throw new MessageException("部门名称已经存在");
            }
        }else{
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
        long userCount = userService.countUserByDepartmentId(departmentId);
        if(userCount > 0){
            throw new MessageException("当前部门或者子部门有成员, 不允许删除");
        }

        DepartmentEntity departmentEntity = getByBusinessId(departmentId);
        Assert.isNull(departmentEntity, "未知的部门");

        List<String> childrenDepartmentIds = listChildrenDepartmentIds(departmentId);
        childrenDepartmentIds.add(departmentId);

        //清空部门负责人
        UpdateWrapper<DepartmentLeaderEntity> leaderWrapper = new UpdateWrapper();
        leaderWrapper.lambda().set(DepartmentLeaderEntity::getDepartmentId, childrenDepartmentIds);
        departmentLeaderMapper.delete(leaderWrapper);

        //删除部门
        removeByBusinessIds(childrenDepartmentIds);
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
    public boolean isExistRootDepartment(String departmentId) {
        Integer flag;
        if(StringUtils.isNotBlank(departmentId)){
            flag = departmentMapper.isExistRootDepartmentByNotDepartmentId(departmentId);
        }else {
            flag = departmentMapper.isExistRootDepartment();
        }
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
                        .forEach(entity -> {
                            departmentLeaderMapper.deleteById(entity.getId());
                            departmentUserMapper.updateSort(entity.getDepartmentId(), entity.getUserId(), 1);
                        });

                //新增
                leaderUserIds.stream()
                        .filter(userId -> !dbLeaderUserIds.contains(userId))
                        .forEach(userId -> {
                            departmentLeaderMapper.insert(new DepartmentLeaderEntity(userId, departmentEntity.getDepartmentId()));
                            departmentUserMapper.updateSort(departmentEntity.getDepartmentId(), userId, 0);
                        });
            }

        }
        saveOrUpdate(departmentEntity);
    }
}