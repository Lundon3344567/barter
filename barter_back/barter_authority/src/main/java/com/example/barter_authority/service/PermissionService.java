package com.example.barter_authority.service;


import com.example.barter_authority.domain.entity.BarterPermissionInfo;
import com.example.barter_authority.domain.form.PageForm;
import com.example.barter_authority.domain.form.PermissionForm;
import com.example.barter_authority.domain.vo.AdminPermissionVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author walnut
 * @version 1.0
 * @className PermissionService
 * @description TODO
 * @date 2021/11/14 3:56 下午
 */
public interface PermissionService {
    /**
     * 通过分页查询所有权限
     *
     * @param pageForm 分页信息
     * @return
     */
    PageInfo<BarterPermissionInfo> queryPermissionByPage(PageForm pageForm);

    /**
     * 添加权限
     *
     * @param permissionForm 前端传来的权限信息
     * @return 权限信息添加返回码
     */
    int addPermission(PermissionForm permissionForm);

    /**
     * 修改权限
     *
     * @param permissionForm 前端传来的权限信息
     * @return 权限信息修改返回码
     */
    int updatePermission(PermissionForm permissionForm);

    /**
     * 删除权限
     *
     * @param id 权限id
     * @return 权限删除返回码
     */
    int deletePermissionById(Long id);

    /**
     * 查询所有的权限
     * @return 所有权限
     */
    List<AdminPermissionVo> queryAllPermission();
}
