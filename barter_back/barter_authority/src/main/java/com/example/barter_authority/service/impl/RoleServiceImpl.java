package com.example.barter_authority.service.impl;


import com.example.barter_authority.common.lang.Const;
import com.example.barter_authority.common.util.*;
import com.example.barter_authority.domain.dto.AdminPermissionDto;
import com.example.barter_authority.domain.entity.BarterPermissionInfo;
import com.example.barter_authority.domain.entity.BarterRoleInfo;
import com.example.barter_authority.domain.entity.BarterRolePermission;
import com.example.barter_authority.domain.entity.BarterUserInfo;
import com.example.barter_authority.domain.form.BarterRoleForm;
import com.example.barter_authority.domain.form.BarterRoleInfoForm;
import com.example.barter_authority.domain.form.RoleUpdateForm;
import com.example.barter_authority.domain.vo.AdminPermissionVo;
import com.example.barter_authority.domain.vo.BarterUserInfoVo;
import com.example.barter_authority.mapper.BarterRoleInfoMapper;
import com.example.barter_authority.mapper.BarterRolePermissionMapper;
import com.example.barter_authority.mapper.BarterUserRoleMapper;
import com.example.barter_authority.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author walnut
 * @version 1.0
 * @className RoleServiceImpl
 * @description TODO
 * @date 2021/11/12 11:53 上午
 */
@Service
public class RoleServiceImpl implements RoleService {

    public static final int NORMAL = 1;

    @Autowired
    private BarterUserRoleMapper userRoleMapper;

    @Autowired
    private BarterRoleInfoMapper roleInfoMapper;

    @Autowired
    private BarterRolePermissionMapper rolePermissionMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FlashUtil flashUtil;

    @Override
    public BarterRoleInfo queryById(Long id) {
        return roleInfoMapper.queryById(id);
    }

    @Override
    public List<BarterRoleInfo> queryRoleByUserId(Long userId) {
        return userRoleMapper.queryAllRoleByUserId(userId);
    }

    @Override
    public int addNormalUserRole(String email) {
        return userRoleMapper.insertNormalUserByEmail(email);
    }

    @Override
    public List<BarterRoleInfo> queryAllRole() {
        return roleInfoMapper.queryAll();
    }

    @Override
    public int deleteById(Long id) {

        if (id <= NORMAL) {
            return -1;
        }
        //判断角色是否还有人拥有
        int userNum = userRoleMapper.queryUserNumByRoleId(id);
        if ( userNum> 0) {
            return  userNum;
        }
        //刷新数据
        flashUtil.flash();
        redisUtil.del("MenuTree");
        return roleInfoMapper.deleteById(id) == 1?0:-2;
    }

    @Override
    public int updateRole(BarterRoleInfoForm roleInfoForm) {

        if (roleInfoForm.getId() <= NORMAL) {
            return -1;
        }

        BarterRoleInfo barterRoleInfo = new BarterRoleInfo();

        BeanUtils.copyProperties(roleInfoForm,barterRoleInfo,BarterRoleInfo.class);
        //刷新数据
        flashUtil.flash();
        redisUtil.del("MenuTree");
        return roleInfoMapper.update(barterRoleInfo) == 1?1:0;
    }

    @Override
    public boolean addRole(BarterRoleForm roleForm) {
        BarterRoleInfo barterRoleInfo = new BarterRoleInfo();
        BeanUtils.copyProperties(roleForm,barterRoleInfo,BarterRoleInfo.class);
        //刷新数据
        flashUtil.flash();
        redisUtil.del("MenuTree");
        return roleInfoMapper.insert(barterRoleInfo) == 1;
    }

    @Override
    public List<AdminPermissionVo> queryPermissionByRoleId(Long roleId) {

        List<BarterPermissionInfo> barterPermissionInfos = rolePermissionMapper.queryPermissionByRoleId(roleId);
        //实体类转换成dto
        List<AdminPermissionDto> permissionDtoList = PermissionUtil.convertToDto(barterPermissionInfos);

        //转换成树状结构
        List<AdminPermissionDto> menuTree = PermissionUtil.convertToTree(permissionDtoList);

        //将dto转换成vo并返回
        return PermissionUtil.convertToVo(menuTree);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePermissionById(RoleUpdateForm roleUpdateForm) {
        System.out.println("roleId" + roleUpdateForm.getRoleId());
        System.out.println("keys" + roleUpdateForm.getKeys());
        List<Integer> oldPermissionId = rolePermissionMapper.queryPermissionIdByRoleId(roleUpdateForm.getRoleId());
        System.out.println("ID==>" + oldPermissionId);

        //获取需要删除的权限id
        List<Integer> deleteRole = CompareUtil.getDifference(oldPermissionId, roleUpdateForm.getKeys());
        System.out.println("DEL=>" + deleteRole);

        //获取需要添加的权限id
        List<Integer> addRole = CompareUtil.getDifference(roleUpdateForm.getKeys(), oldPermissionId);
        System.out.println("ADD=>" + addRole);

        if (!deleteRole.isEmpty()) {
            for (Integer integer : deleteRole) {
                if (rolePermissionMapper.deleteByRecord(roleUpdateForm.getRoleId().longValue(),integer) == 0) {
                    throw new RuntimeException("删除失败");
                }
            }
        }

        if (!addRole.isEmpty()) {
            BarterRolePermission barterRolePermission = new BarterRolePermission();
            barterRolePermission.setRoleId(roleUpdateForm.getRoleId().toString());
            for (Integer integer : addRole) {
                barterRolePermission.setPermissionId(integer.toString());
                if (rolePermissionMapper.insert(barterRolePermission) == 0) {
                    throw new RuntimeException("插入失败");
                }
            }
        }
        //刷新数据
        flashUtil.flash();
        redisUtil.del("MenuTree");
        return Const.UPDATE_SUCCESS;
    }

    @Override
    public List<BarterUserInfoVo> queryUserByRoleId(Long roleId) {

        List<BarterUserInfo> userInfos = userRoleMapper.queryUserByRoleId(roleId);

        return CopyUtil.copyList(userInfos, BarterUserInfoVo.class);
    }
}

