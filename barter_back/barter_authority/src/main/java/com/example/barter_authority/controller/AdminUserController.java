package com.example.barter_authority.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.barter_authority.common.annotation.DoLog;
import com.example.barter_authority.common.lang.Const;
import com.example.barter_authority.common.lang.OperateTypeContains;
import com.example.barter_authority.common.lang.ResultInfo;
import com.example.barter_authority.common.util.ResultInfoUtil;
import com.example.barter_authority.domain.entity.BarterRoleInfo;
import com.example.barter_authority.domain.entity.BarterUserInfo;
import com.example.barter_authority.domain.form.PageForm;
import com.example.barter_authority.domain.form.UserSearchForm;
import com.example.barter_authority.domain.form.UserStateForm;
import com.example.barter_authority.domain.vo.UserVo;
import com.example.barter_authority.service.RoleService;
import com.example.barter_authority.service.UserService;
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
import java.util.regex.Pattern;

/**
 * @author walnut
 * @version 1.0
 * @className AdminController
 * @description 管理员用户操作
 * @date 2021/11/11 3:09 下午
 */
@RestController
@Api(tags="管理员用户管理相关")
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    /**
     * 通过id删除用户
     *
     * @return 删除成功否
     */
    @PreAuthorize("hasAuthority('admin:user:delete')")
    @ApiOperation("通过id删除用户")
    @DeleteMapping("/id/{id}")
    @DoLog(operateModule = "User", operateType = OperateTypeContains.OPERATE_DELETE, operateDesc = "删除用户")
    public ResultInfo<String> deleteUser(
            @PathVariable(name = "id") Long id
    ) {
        int rows = userService.deleteUserById(id);
        if (rows >= 1) {
            return ResultInfoUtil.buildSuccess("删除成功");
        } else {
            return ResultInfoUtil.buildError("删除失败");
        }
    }

    /**
     * 通过json格式插入用户
     *
     * @param jsonObject 前端返回
     * @return 插入结果
     */
    @PreAuthorize("hasAuthority('admin:user:save')")
    @ApiOperation("插入用户")
    @PostMapping("")
    @DoLog(operateModule = "User", operateType = OperateTypeContains.OPERATE_INSERT, operateDesc = "插入用户")
    public ResultInfo<String> insertUser(@RequestBody JSONObject jsonObject) {

        //将JSONObject转换为对应实体类
        BarterUserInfo userInfo = JSON.parseObject(JSON.toJSONString(jsonObject ), BarterUserInfo.class);

        int i = userService.insertUser(userInfo);
        if (i > 0) {
            return ResultInfoUtil.buildSuccess("插入成功");
        } else {
            return ResultInfoUtil.buildError("插入失败");
        }
    }

    /**
     * 更新用户
     *
     * @param jsonObject 更新的用户信息
     * @return 更新成功？
     */
    @PreAuthorize("hasAuthority('admin:user:update')")
    @ApiOperation("更新用户")
    @PutMapping("")
    @DoLog(operateModule = "User", operateType = OperateTypeContains.OPERATE_UPDATE, operateDesc = "更新用户")
    public ResultInfo<String> updateUser(@RequestBody JSONObject jsonObject) {

        BarterUserInfo userInfo = JSON.parseObject(JSON.toJSONString(jsonObject), BarterUserInfo.class);

        int i = userService.updateUser(userInfo);
        if (i > 0) {
            return ResultInfoUtil.buildSuccess("更新成功");
        } else {
            return ResultInfoUtil.buildError("更新失败");
        }
    }

    /**
     * 分页查询用户接口
     *
     * @param pageNum 页码
     * @param pageSize 单页条数
     * @return 用户信息
     */
    @PreAuthorize("hasAuthority('admin:user:list')")
    @ApiOperation("分页查询用户接口")
    @GetMapping("/page")
    @DoLog(operateModule = "User", operateType = OperateTypeContains.OPERATE_SELECT, operateDesc = "分页查询所有用户")
    public ResultInfo<PageInfo<BarterUserInfo>> queryAllUser(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize
    ) {
        PageForm pageForm = new PageForm(pageNum, pageSize);

        PageInfo<BarterUserInfo> pageInfo = userService.queryUserByPage(pageForm);

        return ResultInfoUtil.buildSuccess(pageInfo);
    }

    /**
     * 获取指定用户数据
     *
     * @param id 用户id
     * @return 指定用户数据
     */
    @PreAuthorize("hasAuthority('admin:user:list')")
    @ApiOperation("获取指定用户数据接口")
    @GetMapping("/id/{id}")
    @DoLog(operateModule = "User", operateType = OperateTypeContains.OPERATE_SELECT, operateDesc = "查询用户权限")
    public ResultInfo<BarterUserInfo> queryUserById(@PathVariable(name = "id") Long id) {

        BarterUserInfo barterUserInfo = userService.queryUserById(id);
        return ResultInfoUtil.buildSuccess(barterUserInfo);
    }

    /**
     * 查询用户拥有的角色
     *
     * @return 角色信息
     */
    @GetMapping("/{id}/role")
    @ApiOperation("查询用户拥有的角色")
    public ResultInfo<List<BarterRoleInfo>> getRoleByUsername(@PathVariable(name = "id") Long userId) {
        List<BarterRoleInfo> barterRoleInfos = roleService.queryRoleByUserId(userId);
        if (barterRoleInfos.isEmpty()) {
            return ResultInfoUtil.buildError("用户不存在或此用户未分配角色");
        }
        return ResultInfoUtil.buildSuccess(barterRoleInfos);
    }

    /**
     * 为用户重新分配角色
     *
     * @param jsonObject 用户id 和 角色id
     * @return 分配是否成功
     */
    @PutMapping("/role")
    @ApiOperation("重新分配用户拥有的角色")
    @DoLog(operateModule = "USER", operateType = OperateTypeContains.OPERATE_INSERT, operateDesc = "更改用户角色")
    public ResultInfo<String> updateRoleByUsername(@RequestBody @Valid JSONObject jsonObject) throws Exception {

        int id = (int)jsonObject.get("id");
        List<Integer> roleIds = (List<Integer>)jsonObject.get("roleIds");

        if (userService.updateUserRoleByRoleId((long)id,roleIds) == Const.OK){
            return ResultInfoUtil.buildSuccess("角色分配成功");
        }

        return ResultInfoUtil.buildError("角色分配发生错误");
    }


    @ApiOperation("更改用户状态")
    @PutMapping("/state")
    public ResultInfo<String> updateSelf(@RequestBody @Valid UserStateForm userStateForm) {
//        int userId = (int)jsonObject.get("userId");
//        int state = (int)jsonObject.get("state");
        if (userService.changeUserState(userStateForm) == 1) {
            return ResultInfoUtil.buildSuccess("更改成功");
        }
        else {
            return ResultInfoUtil.buildError("更改失败");
        }
    }

    /**
     *
     * @param condition 查询信息
     * @return 查询匹配用户
     */
    @PreAuthorize("hasAuthority('admin:user:list')")
    @ApiOperation("查询匹配用户")
    @GetMapping("/condition")
    @DoLog(operateModule = "User", operateType = OperateTypeContains.OPERATE_SELECT, operateDesc = "分页查询所有用户")
    public ResultInfo<PageInfo<UserVo>> queryAllUser(
            @Valid UserSearchForm condition
    ) {
        //判断是否有非法参数
        String patten = "^10[1-3]|[1-3]$";
        if (condition.getState() != null && !Pattern.matches(patten,condition.getState().toString())){
            return ResultInfoUtil.buildError("不合法的用户状态");
        }
        PageInfo<UserVo> pageInfo = userService.queryUserByCondition(condition);

        return ResultInfoUtil.buildSuccess(pageInfo);
    }
}

