package com.example.barter_authority.service.impl;


import com.example.barter_authority.common.util.FlashUtil;
import com.example.barter_authority.common.util.PermissionUtil;
import com.example.barter_authority.common.util.RedisUtil;
import com.example.barter_authority.domain.dto.AdminPermissionDto;
import com.example.barter_authority.domain.entity.BarterPermissionInfo;
import com.example.barter_authority.domain.entity.BarterUserInfo;
import com.example.barter_authority.domain.vo.AdminPermissionVo;
import com.example.barter_authority.mapper.BarterPermissionInfoMapper;
import com.example.barter_authority.service.MenuService;
import com.example.barter_authority.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author walnut
 * @version 1.0
 * @className MenuServiceImpl
 * @description TODO
 * @date 2021/11/21 3:15 下午
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private UserService userService;

    @Autowired
    private BarterPermissionInfoMapper permissionInfoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FlashUtil flashUtil;


    @Override
    public List<AdminPermissionVo> getCurrentUserNav() {
        //TODO
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //在不携带token的情况下，principal为UserDetail类型

        if (redisUtil.hasKey("MenuTree:" + username)) {
            return (List<AdminPermissionVo>) redisUtil.get("MenuTree:" + username);
        }

        BarterUserInfo userInfo = userService.queryUserByUsername(username);

        //获取用户拥有的对应权限
        List<Long> menuIds = permissionInfoMapper.getNavMenuIds(userInfo.getUserId());
        List<BarterPermissionInfo> barterPermissionInfos;
        if (menuIds.size() > 0) {
            barterPermissionInfos = permissionInfoMapper.queryByIds(menuIds);
        } else {
            return null;
        }

        //实体类转换成dto
        List<AdminPermissionDto> permissionDtoList = PermissionUtil.convertToDto(barterPermissionInfos);


        //转换成树状结构
        List<AdminPermissionDto> menuTree = PermissionUtil.convertToTree(permissionDtoList);

        //将dto转换成vo并返回
        List<AdminPermissionVo> adminPermissionVos = PermissionUtil.convertToVo(menuTree);
        //TODO
        //将菜单树结构存放到redis里
        redisUtil.set("MenuTree:" + username, adminPermissionVos, 60 * 60);
        flashUtil.add("MenuTree:" + username);

        return adminPermissionVos;
    }


}

