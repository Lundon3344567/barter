package com.example.barter_log.common.util;

import com.example.barter_authority.domain.dto.AdminPermissionDto;
import com.example.barter_authority.domain.entity.BarterPermissionInfo;
import com.example.barter_authority.domain.vo.AdminPermissionVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author walnut
 * @version 1.0
 * @className PermissionUtil
 * @description 权限转换工具类
 * @date 2021/11/23 4:02 下午
 */
public class PermissionUtil {
    /**
     * 将树状的permissionDtoList转换为树状的树状的permissionVoList
     *
     * @param menuTree 树状的permissionDtoList
     * @return AdminPermissionVo
     */
    public static List<AdminPermissionVo> convertToVo(List<AdminPermissionDto> menuTree) {

        List<AdminPermissionVo> adminPermissionVoList = new ArrayList<>();

        for (AdminPermissionDto permissionDto : menuTree) {
            AdminPermissionVo adminPermissionVo = new AdminPermissionVo();
            if (permissionDto.getChildren().size() > 0) {
                // 子节点调用当前方法进行再次转换
                adminPermissionVo.setChildren(convertToVo(permissionDto.getChildren()));
            }
            BeanUtils.copyProperties(permissionDto, adminPermissionVo, AdminPermissionVo.class);
            adminPermissionVoList.add(adminPermissionVo);
        }
        return adminPermissionVoList;
    }

    /**
     * 将AdminPermissionDto转换为树状的AdminPermissionDto
     *
     * @param permissionDtoList AdminPermissionDto
     * @return 树状的AdminPermissionDto
     */
    public static List<AdminPermissionDto> convertToTree(List<AdminPermissionDto> permissionDtoList) {
        List<AdminPermissionDto> finalMenus = new ArrayList<>();

        // 先各自寻找到各自的孩子
        for (AdminPermissionDto menu : permissionDtoList) {

            for (AdminPermissionDto e : permissionDtoList) {
                if (menu.getId().equals(e.getParentId())) {
                    menu.getChildren().add(e);
                }
            }

            // 提取出父节点
            if (menu.getParentId() == 0L) {
                finalMenus.add(menu);
            }
        }
        return finalMenus;
    }

    /**
     * 将BarterPermissionInfo 转换为 AdminPermissionDto
     *
     * @param permissionInfos 转换前对象
     * @return 转换后的AdminPermissionDto对象
     */
    public static List<AdminPermissionDto> convertToDto(List<BarterPermissionInfo> permissionInfos) {
        List<AdminPermissionDto> adminPermissionDtoList = new ArrayList<>();

        for (BarterPermissionInfo permissionInfo : permissionInfos) {
            AdminPermissionDto adminPermissionDto = new AdminPermissionDto();

            adminPermissionDto.setTitle(permissionInfo.getName());
            adminPermissionDto.setComponent(permissionInfo.getComponent());
            adminPermissionDto.setIcon(permissionInfo.getIcon());
            adminPermissionDto.setId(permissionInfo.getId());
            adminPermissionDto.setParentId(permissionInfo.getParentId());
            adminPermissionDto.setName(permissionInfo.getPerms());
            adminPermissionDto.setPath(permissionInfo.getPath());

            adminPermissionDtoList.add(adminPermissionDto);
        }
        return adminPermissionDtoList;
    }
}

