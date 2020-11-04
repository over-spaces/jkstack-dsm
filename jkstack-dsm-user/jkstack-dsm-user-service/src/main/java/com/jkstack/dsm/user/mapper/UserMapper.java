package com.jkstack.dsm.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkstack.dsm.user.controller.vo.UserSimpleVO;
import com.jkstack.dsm.user.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author lifang
 * @since 2020-09-30
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 分页获取部门下人员列表
     *
     * @param departmentId 部门ID
     * @param page         分页信息
     * @return 用户列表
     */
    IPage<UserEntity> selectPageUserByDepartmentId(String departmentId, IPage<UserEntity> page);

    /**
     * 分页获取非指定部门下人员列表
     *
     * @param departmentId 部门ID
     * @param page         分页信息
     * @return 用户列表
     */
    IPage<UserEntity> selectPageUserByNotDepartmentId(String departmentId, IPage<UserEntity> page);

    /**
     * 分页获取非指定部门下人员列表
     *
     * @param departmentId 部门ID
     * @param page         分页信息
     * @param condition    模糊查询条件
     * @return 用户列表
     */
    IPage<UserEntity> selectPageUserByDepartmentIdAndLike(String departmentId, String condition, IPage<UserEntity> page);


    /**
     * 分页获取非指定部门下人员列表，并且模糊查询
     *
     * @param departmentId 部门ID
     * @param condition    模糊查询条件
     * @param page         分页信息
     * @return 用户列表
     */
    IPage<UserEntity> selectPageUserByNotDepartmentIdAndLike(String departmentId, String condition, IPage<UserEntity> page);

    List<UserSimpleVO> listByDepartmentIds(@Param("list") Collection<String> departmentIds);

    IPage<UserEntity> listByWorkGroupId(String workGroupId, IPage<UserEntity> page);

    /**
     * 模糊查询用户列表
     *
     * @param condition 模糊查询条件
     * @param page      分页信息
     * @return 用户列表
     */
    IPage<UserEntity> selectPageByLike(String condition, IPage<UserEntity> page);

    /**
     * 获取部门下部门组织人员列表
     *
     * @param departmentId 部门ID
     * @return 部门组织列表
     */
    List<UserEntity> selectLeaderUserListByDepartmentId(String departmentId);

    /**
     * 分页查询用户部分信息列表，并且排除指定工作组下的人员
     *
     * @param departmentIds 部门ID列表
     * @param workGroupId   工作组ID
     * @param page          分页信息
     * @return 用户列表
     */
    IPage<UserSimpleVO> selectPageByDepartmentIdsAndNotWorkGroupId(@Param("list") List<String> departmentIds, @Param("workGroupId") String workGroupId, @Param("page") IPage<UserEntity> page);

    /**
     * 分页查询用户部分信息列表，并且排除指定工作组下的人员
     *
     * @param workGroupId 工作组ID
     * @param page        分页信息
     * @return 用户列表
     */
    IPage<UserSimpleVO> selectPageByNotWorkGroupId(String workGroupId, Page<Object> page);

    /**
     * 分页查询用户部分信息列表，并且排除指定工作组下的人员
     *
     * @param workGroupId 工作组ID
     * @param condition   模糊查询条件
     * @param page        分页信息
     * @return 用户列表
     */
    IPage<UserSimpleVO> selectPageByNotWorkGroupIdAndLike(String workGroupId, String condition, @Param("page") Page<Object> page);


    /**
     * 分页查询用户部分信息列表
     *
     * @param departmentIds 部门ID列表
     * @param page          分页信息
     * @return 用户列表
     */
    IPage<UserSimpleVO> selectPageByDepartmentIds(@Param("list") List<String> departmentIds, @Param("page") Page<Object> page);

    /**
     * 分页查询用户部分信息列表
     *
     * @param departmentIds 部门ID列表
     * @param condition     模糊查询条件
     * @param page          分页信息
     * @return 用户列表
     */
    IPage<UserSimpleVO> selectPageByDepartmentIdsAndLike(@Param("list") List<String> departmentIds, String condition, @Param("page") Page<Object> page);


    /**
     * 分页查询用户部分信息列表,并且支持模糊查询
     *
     * @param departmentIds 部门ID列表
     * @param workGroupId   工作组ID
     * @param condition     模糊查询条件
     * @param page          分页信息
     * @return 用户列表
     */
    IPage<UserSimpleVO> selectPageByDepartmentIdsAndNotWorkGroupIdAndLike(@Param("list") List<String> departmentIds, String workGroupId, String condition, @Param("page") Page<Object> page);


    /**
     * 分页查询用户部分信息列表,并且支持模糊查询
     *
     * @param page 分页信息
     * @return 用户列表
     */
    IPage<UserSimpleVO> selectPageSimpleUserList(@Param("page") Page<Object> page);

    /**
     * 分页查询用户部分信息列表,并且支持模糊查询
     *
     * @param condition 模糊查询条件
     * @param page      分页信息
     * @return 用户列表
     */
    IPage<UserSimpleVO> selectPageSimpleUserListByLike(String condition, @Param("page") Page<Object> page);

}