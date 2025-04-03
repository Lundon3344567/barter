package com.example.barter_authority.controller;

import com.example.barter_authority.common.annotation.DoLog;
import com.example.barter_authority.common.lang.Const;
import com.example.barter_authority.common.lang.OperateTypeContains;
import com.example.barter_authority.common.lang.ResultInfo;
import com.example.barter_authority.common.util.ResultInfoUtil;
import com.example.barter_authority.domain.entity.BarterPermissionInfo;
import com.example.barter_authority.domain.form.PageForm;
import com.example.barter_authority.domain.form.PermissionForm;
import com.example.barter_authority.domain.vo.AdminPermissionVo;
import com.example.barter_authority.service.PermissionService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author walnut
 * @version 1.0
 * @className AdminPermissionController
 * @description 管理员对权限管理的接口类
 * @date 2021/11/14 3:50 下午
 */
@RestController
@Api(tags = "管理员权限管理相关")
@RequestMapping("/admin/permission")
public class AdminPermissionController {
    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);
    @Autowired
    private PermissionService permissionService;

    @PreAuthorize("hasAuthority('admin:permission:list')")
    @GetMapping("/page")
    @ApiOperation("分页查询所有权限")
    public ResultInfo<PageInfo<BarterPermissionInfo>> getAllPermission(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize
    ) {
        PageForm pageForm = new PageForm(pageNum, pageSize);

        PageInfo<BarterPermissionInfo> map = permissionService.queryPermissionByPage(pageForm);

        return ResultInfoUtil.buildSuccess(map);
    }

    @PreAuthorize("hasAuthority('admin:permission:list')")
    @GetMapping("/")
    @ApiOperation("查询所有权限")
    public ResultInfo<List<AdminPermissionVo>> getPermissions() {

        List<AdminPermissionVo> permissionVos = permissionService.queryAllPermission();

        return ResultInfoUtil.buildSuccess(permissionVos);
    }

    @PreAuthorize("hasAuthority('admin:permission:save')")
    @PostMapping()
    @ApiOperation("添加权限")
    @DoLog(operateModule = "PERMISSION", operateType = OperateTypeContains.OPERATE_INSERT, operateDesc = "添加权限")
    public ResultInfo<String> insertPermission(@RequestBody PermissionForm permissionForm) {
//TODO 待排查
        if (permissionService.addPermission(permissionForm) == 1) {
            return ResultInfoUtil.buildSuccess("插入成功");
        } else {
            return ResultInfoUtil.buildError("插入失败");
        }
    }

    @PreAuthorize("hasAuthority('admin:permission:update')")
    @PutMapping()
    @ApiOperation("修改权限")
    @DoLog(operateModule = "PERMISSION", operateType = OperateTypeContains.OPERATE_UPDATE, operateDesc = "修改权限")
    public ResultInfo<String> updatePermission(@RequestBody @Valid PermissionForm permissionForm) {
        //TODO 待排查
        LOG.info(String.valueOf(permissionForm));
        if (permissionService.updatePermission(permissionForm) == 1) {
            return ResultInfoUtil.buildSuccess("修改成功");
        } else {
            return ResultInfoUtil.buildError("修改失败");
        }
    }

    @PreAuthorize("hasAuthority('admin:permission:delete')")
    @DeleteMapping("/{id}")
    @ApiOperation("删除权限")
    @DoLog(operateModule = "PERMISSION", operateType = OperateTypeContains.OPERATE_DELETE, operateDesc = "删除权限")
    public ResultInfo<String> deletePermission(@PathVariable(name = "id")Long id){
        int code = permissionService.deletePermissionById(id);
        if (code == Const.OK){
            return ResultInfoUtil.buildSuccess("权限删除成功");
        }
        if (code == Const.NOT_ALLOWED) {
            return ResultInfoUtil.buildError("请清理用户拥有的此权限后重试");
        }
        return ResultInfoUtil.buildError("删除失败，请联系管理员");
    }

}

