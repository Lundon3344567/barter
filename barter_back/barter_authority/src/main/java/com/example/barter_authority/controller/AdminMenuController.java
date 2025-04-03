package com.example.barter_authority.controller;

import cn.hutool.core.map.MapUtil;
import com.example.barter_authority.common.annotation.DoLog;
import com.example.barter_authority.common.lang.OperateTypeContains;
import com.example.barter_authority.common.lang.ResultInfo;
import com.example.barter_authority.common.util.RedisUtil;
import com.example.barter_authority.common.util.ResultInfoUtil;
import com.example.barter_authority.domain.entity.BarterUserInfo;
import com.example.barter_authority.domain.vo.AdminPermissionVo;
import com.example.barter_authority.service.MenuService;
import com.example.barter_authority.service.PermissionService;
import com.example.barter_authority.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * @author walnut
 * @version 1.0
 * @className AdminMenuController
 * @description 管理员菜单相关获取
 * @date 2021/11/21 3:12 下午
 */
@RestController
@Api(tags = "管理员菜单获取相关")
@RequestMapping("/admin/menu")
public class AdminMenuController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/")
    @ApiOperation("获取用户对应的菜单列表")
    @DoLog(operateModule = "Menu", operateType = OperateTypeContains.OPERATE_SELECT, operateDesc = "获取用户对应的菜单列表")
    public ResultInfo<Map<Object, Object>> nav(Principal principal) {
        BarterUserInfo userInfo = userService.queryUserByUsername(principal.getName());

        //获取权限信息
        String userAuthorityInfo = userService.getUserAuthorityInfo(userInfo.getUserId());
        String[] authorityArray = StringUtils.tokenizeToStringArray(userAuthorityInfo, ",");

        //获取导航栏信息
        List<AdminPermissionVo> navs = menuService.getCurrentUserNav();

        return ResultInfoUtil.buildSuccess(MapUtil.builder()
                .put("authoritys", authorityArray)
                .put("nav", navs)
                .map()
        );
    }


}

