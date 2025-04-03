package com.example.barter_authority.controller;

import com.example.barter_authority.common.annotation.DoLog;
import com.example.barter_authority.common.lang.LogConstants;
import com.example.barter_authority.common.lang.OperateTypeContains;
import com.example.barter_authority.common.lang.ResultInfo;
import com.example.barter_authority.common.util.ResultInfoUtil;
import com.example.barter_authority.domain.entity.BarterRoleInfo;
import com.example.barter_authority.domain.form.BarterRoleForm;
import com.example.barter_authority.domain.form.BarterRoleInfoForm;
import com.example.barter_authority.domain.form.RoleUpdateForm;
import com.example.barter_authority.domain.vo.AdminPermissionVo;
import com.example.barter_authority.domain.vo.BarterUserInfoVo;
import com.example.barter_authority.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author walnut
 * @version 1.0
 * @date 2021/11/9 5:27 下午
 */
@RestController
@Api(tags = "管理员角色管理相关")
@RequestMapping("/admin/role")
public class AdminRoleController {
    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);
    @Autowired
    private RoleService roleService;

    /**
     * 获取所有的role
     * @return Role列表
     */
    @GetMapping("/")
    @ApiOperation("获取所有的角色信息")
    public ResultInfo<List<BarterRoleInfo>> getAllRole() {
        List<BarterRoleInfo> barterRoleInfos = roleService.queryAllRole();
        return ResultInfoUtil.buildSuccess(barterRoleInfos);
    }

    /**
     * 通过角色id获取角色
     * @param id 角色id
     * @return 角色信息
     */
    @GetMapping("/{id}")
    @ApiOperation("通过角色id获取角色")
    public ResultInfo<BarterRoleInfo> getRoleById(@PathVariable(name = "id")Long id) {
        BarterRoleInfo barterRoleInfo = roleService.queryById(id);
        return ResultInfoUtil.buildSuccess(barterRoleInfo);
    }

    /**
     * 通过roleId删除角色
     * @param id 角色id
     * @return 删除信息
     */
    @DeleteMapping("/{id}")
    @ApiOperation("通过角色id删除角色")
    @DoLog(operateModule = "ROLE", operateType = OperateTypeContains.OPERATE_DELETE, operateDesc = "通过角色id删除角色")
    public ResultInfo<String> deleteRoleById(@PathVariable(name = "id")Long id) {

        int flag = roleService.deleteById(id);
        if (flag > 0){
            return ResultInfoUtil.buildError("角色被"+flag+"位用户拥有，无法删除");
        }
        if (flag == 0) {
            return ResultInfoUtil.buildSuccess("更新角色成功");
        } else if (flag == -2){
            return ResultInfoUtil.buildError("更新角色失败");
        } else {
            return ResultInfoUtil.buildError("基础角色不允许更改");
        }
    }

    /**
     * 通过id更新角色信息
     * @param roleInfoForm 角色信息
     * @return 更新信息
     */
    @PutMapping("/")
    @ApiOperation("通过角色id更新角色信息")
    @DoLog(operateModule = "ROLE", operateType = OperateTypeContains.OPERATE_UPDATE, operateDesc = "通过角色id更新角色信息",requestStyle = LogConstants.REQUEST_COMMON_STYLE)
    public ResultInfo<String> updateRoleById(@RequestBody @Valid BarterRoleInfoForm roleInfoForm) {

        int flag = roleService.updateRole(roleInfoForm);
        if (flag == 1) {
            return ResultInfoUtil.buildSuccess("更新角色成功");
        } else if (flag == 0){
            return ResultInfoUtil.buildError("更新角色失败");
        } else {
            return ResultInfoUtil.buildError("基础角色不允许更改");
        }
    }

    /**
     * 添加角色
     * @return 添加信息
     */
    @PostMapping("/")
    @ApiOperation("添加角色")
    @DoLog(operateModule = "Role", operateType = OperateTypeContains.OPERATE_INSERT, operateDesc = "添加角色")
    public ResultInfo<String> addRoleById(@RequestBody BarterRoleForm roleForm) {
        if (roleService.addRole(roleForm)) {
            return ResultInfoUtil.buildSuccess("添加角色成功");
        } else {
            return ResultInfoUtil.buildError("添加角色失败");
        }
    }

    /**
     * 获取角色拥有的权限
     * @return 角色拥有权限
     */
    @GetMapping("/{roleId}/permission")
    @ApiOperation("获取角色拥有的权限")
    public ResultInfo<List<AdminPermissionVo>> getPermissionById(@PathVariable(name = "roleId")Long roleId) {
        List<AdminPermissionVo> permissionInfos = roleService.queryPermissionByRoleId(roleId);
        return ResultInfoUtil.buildSuccess(permissionInfos);
    }

    @PutMapping("/permission")
    @ApiOperation("更新角色拥有的权限")
    @DoLog(operateModule = "ROLE_PERMISSION", operateType = OperateTypeContains.OPERATE_UPDATE, operateDesc = "更新角色拥有的权限")
    public ResultInfo<String> updatePermissionByRoleId(@RequestBody @Valid RoleUpdateForm roleUpdateForm) {

        LOG.info(roleUpdateForm.toString());


        if (roleService.updatePermissionById(roleUpdateForm) == 1) {
            return ResultInfoUtil.buildSuccess("修改成功");
        }
        return ResultInfoUtil.buildError("修改失败");
    }

    @GetMapping("/{roleId}/users")
    @ApiOperation("获取拥有角色的用户列表")
    public ResultInfo<List<BarterUserInfoVo>> getUserById(@PathVariable(name = "roleId")Long roleId) {
        List<BarterUserInfoVo> permissionInfos = roleService.queryUserByRoleId(roleId);
        return ResultInfoUtil.buildSuccess(permissionInfos);
    }

}

