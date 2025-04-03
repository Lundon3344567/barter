package com.example.barter_authority.service;


import com.example.barter_authority.domain.vo.AdminPermissionVo;

import java.util.List;

/**
 * @author walnut
 * @version 1.0
 * @className MenuService
 * @description TODO
 * @date 2021/11/21 3:15 下午
 */
public interface MenuService {

    /**
     * 获取管理员用户的菜单
     * @return 管理员用户菜单
     */
    List<AdminPermissionVo> getCurrentUserNav();
}
