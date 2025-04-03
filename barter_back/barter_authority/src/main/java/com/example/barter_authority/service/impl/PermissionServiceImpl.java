package com.example.barter_authority.service.impl;

import com.example.barter_authority.common.lang.Const;
import com.example.barter_authority.common.util.FlashUtil;
import com.example.barter_authority.common.util.PermissionUtil;
import com.example.barter_authority.common.util.RedisUtil;
import com.example.barter_authority.domain.dto.AdminPermissionDto;
import com.example.barter_authority.domain.entity.BarterPermissionInfo;
import com.example.barter_authority.domain.form.PageForm;
import com.example.barter_authority.domain.form.PermissionForm;
import com.example.barter_authority.domain.vo.AdminPermissionVo;
import com.example.barter_authority.mapper.BarterPermissionInfoMapper;
import com.example.barter_authority.mapper.BarterRolePermissionMapper;
import com.example.barter_authority.service.PermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author walnut
 * @version 1.0
 * @className PermissionServiceImpl
 * @description TODO
 * @date 2021/11/14 3:58 下午
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private BarterPermissionInfoMapper permissionInfoMapper;

    @Autowired
    private BarterRolePermissionMapper rolePermissionMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FlashUtil flashUtil;

    @Override
    public PageInfo queryPermissionByPage(PageForm pageForm) {

        PageHelper.startPage(pageForm.getRequestPage(), pageForm.getPageSize());

        //查询整体
        List<BarterPermissionInfo> barterPermissionInfos = permissionInfoMapper.queryAll();

        return new PageInfo<BarterPermissionInfo>(barterPermissionInfos);

    }

    @Override
    public int addPermission(PermissionForm permissionForm) {
        //TEST TODO
        System.out.println(permissionForm);
        BarterPermissionInfo barterPermissionInfo = new BarterPermissionInfo();
        //处理前端要插入的权限信息
        BeanUtils.copyProperties(permissionForm,barterPermissionInfo,BarterPermissionInfo.class);
        barterPermissionInfo.setCreated(new Date());
        barterPermissionInfo.setUpdated(new Date());
        barterPermissionInfo.setStatu(1);

        //刷新数据
        flashUtil.flash();
        redisUtil.del("MenuTree");

        return permissionInfoMapper.insert(barterPermissionInfo);
    }

    @Override
    public int updatePermission(PermissionForm permissionForm) {

        BarterPermissionInfo barterPermissionInfo = new BarterPermissionInfo();

        BeanUtils.copyProperties(permissionForm,barterPermissionInfo,BarterPermissionInfo.class);

        barterPermissionInfo.setId(Integer.toUnsignedLong(permissionForm.getId()));

        barterPermissionInfo.setUpdated(new Date());

        //刷新数据
        flashUtil.flash();
        redisUtil.del("MenuTree");

        return permissionInfoMapper.update(barterPermissionInfo);
    }

    @Override
    public int deletePermissionById(Long id) {
       if (rolePermissionMapper.countByPermissionId(id) != 0){
           //权限有人拥有，不允许删除
           return Const.NOT_ALLOWED;
       } else {
           int i = permissionInfoMapper.deleteById(id);
           if (i != 1){
               //删除失败
               return Const.FILED;
           } else {
               //删除成功

               //刷新数据
               flashUtil.flash();
               redisUtil.del("MenuTree");

               return Const.OK;
           }
       }
    }

    @Override
    public List<AdminPermissionVo> queryAllPermission() {

        if (redisUtil.hasKey("MenuTree:")){
            return (List<AdminPermissionVo>)redisUtil.get("MenuTree:");
        }

        List<BarterPermissionInfo> barterPermissionInfos = permissionInfoMapper.queryAll();

        //实体类转换成dto
        List<AdminPermissionDto> permissionDtoList = PermissionUtil.convertToDto(barterPermissionInfos);

        //转换成树状结构
        List<AdminPermissionDto> menuTree = PermissionUtil.convertToTree(permissionDtoList);

        //将dto转换成vo并返回
        List<AdminPermissionVo> adminPermissionVos = PermissionUtil.convertToVo(menuTree);

        //将菜单树结构存放到redis里
        redisUtil.set("MenuTree:" , adminPermissionVos, 60 * 60);

        return adminPermissionVos;
    }
}

