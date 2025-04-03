package com.example.barter_authority.service;


import com.example.barter_authority.domain.entity.BarterRoleInfo;
import com.example.barter_authority.domain.form.BarterRoleForm;
import com.example.barter_authority.domain.form.BarterRoleInfoForm;
import com.example.barter_authority.domain.form.RoleUpdateForm;
import com.example.barter_authority.domain.vo.AdminPermissionVo;
import com.example.barter_authority.domain.vo.BarterUserInfoVo;

import java.util.List;

/**
 * @author walnut
 * @version 1.0
 * @className RoleService
 * @description TODO
 * @date 2021/11/12 11:53 上午
 */
public interface RoleService {
    /**
     * @param id 角色id
     * @return 角色信息
     */
    BarterRoleInfo queryById(Long id);

    /**
     * 通过用户id查询角色
     *
     * @param userId 用户id
     * @return List<BarterRoleInfo> 角色列表
     */
    List<BarterRoleInfo> queryRoleByUserId(Long userId);

    /**
     * 加入用户，用户创建
     *
     * @param email 用户email
     * @return 影响行数
     */
    int addNormalUserRole(String email);

    /**
     * 查询所有的角色
     *
     * @return 所有的角色
     */
    List<BarterRoleInfo> queryAllRole();

    /**
     * 通过id删除角色
     *
     * @param id 角色id
     * @return 删除成功？
     */
    int deleteById(Long id);

    /**
     * 更新角色信息
     *
     * @param roleInfoForm 角色信息
     * @return 更新成功？
     */
    int updateRole(BarterRoleInfoForm roleInfoForm);

    /**
     * 增加角色信息
     *
     * @param roleForm 角色信息
     * @return 增加成功？
     */
    boolean addRole(BarterRoleForm roleForm);

    /**
     * 通过角色查询角色拥有的权限
     *
     * @param roleId 角色id
     * @return 角色拥有的权限
     */
    List<AdminPermissionVo> queryPermissionByRoleId(Long roleId);

    /**
     * 通过角色的权限id更新权限的信息
     *
     * @param roleUpdateForm 角色id,角色拥有的权限id,操作类型 1 添加 0 删除
     * @return 更新成功？
     */
    int updatePermissionById(RoleUpdateForm roleUpdateForm);

    /**
     * 查询拥有这个角色的用户列表
     * @param roleId 角色id
     * @return 用户罗彪
     */

    List<BarterUserInfoVo> queryUserByRoleId(Long roleId);
}
