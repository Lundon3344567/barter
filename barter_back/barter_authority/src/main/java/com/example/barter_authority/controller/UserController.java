package com.example.barter_authority.controller;

import com.alibaba.fastjson.JSON;
import com.example.barter_authority.common.lang.Const;
import com.example.barter_authority.common.lang.ResultInfo;
import com.example.barter_authority.common.util.ResultInfoUtil;
import com.example.barter_authority.domain.entity.BarterUserInfo;
import com.example.barter_authority.domain.form.PersonalInfoForm;
import com.example.barter_authority.domain.form.RegisterForm;
import com.example.barter_authority.domain.vo.SelfInfoVo;
import com.example.barter_authority.domain.vo.SellerInfoVo;
import com.example.barter_authority.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * @Author walnut
 * @Date 2021/10/26 5:00 下午
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@Api(tags = "普通用户用户操作")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 普通用户注册接口
     * @param request 注册的普通用户(携带于getSession().getAttribute)
     * @return ResultInfo 统一返回请求
     */
    @ApiOperation("普通用户注册接口")
    @PostMapping("/register")
    public ResultInfo<String> register(HttpServletRequest request) {
        String form = (String) request.getSession().getAttribute("form");
        RegisterForm registerForm = JSON.parseObject(form, RegisterForm.class);

        if (userService.judgeUsernameExist(registerForm.getUsername())) {
            return ResultInfoUtil.buildError("用户名已存在");
        }

        if (userService.userRegister(registerForm) == Const.OK){
            return ResultInfoUtil.buildSuccess("注册成功");
        }else{
            return ResultInfoUtil.buildError("注册失败");
        }
    }

    /**
     * 微信小程序普通用户注册接口
     * @param request 注册的普通用户(携带于getSession().getAttribute)
     * @return ResultInfo 统一返回请求
     */
    @ApiOperation("微信普通用户注册接口")
    @PostMapping("/register-wechat")
    public ResultInfo<String> registerWechat(HttpServletRequest request) {
        String form = (String) request.getSession().getAttribute("form");
        RegisterForm registerForm = JSON.parseObject(form, RegisterForm.class);

        if (userService.judgeUsernameExist(registerForm.getUsername())) {
            return ResultInfoUtil.buildError("用户名已存在");
        }

        if (userService.userRegister(registerForm) == Const.OK){
            return ResultInfoUtil.buildSuccess("注册成功");
        }else{
            return ResultInfoUtil.buildError("注册失败");
        }
    }

    /**
     * 判断用户名已存在
     * @param username 用户名
     * @return 已存在？
     */
    @ApiOperation("判断用户名已存在")
    @GetMapping
    public ResultInfo<String> judgeUsernameExist(@RequestParam(name = "username")String username){
        if (userService.judgeUsernameExist(username)) {
            return ResultInfoUtil.buildError("用户名已存在");
        }
        return ResultInfoUtil.buildSuccess("用户名可注册");
    }

    /**
     * 查询卖家的信息
     * @param id 卖家id
     * @return 卖家信息
     */
    @ApiOperation("查询卖家信息")
    @GetMapping("/seller/{id}")
    public ResultInfo<SellerInfoVo> querySeller(@Min(1) @PathVariable(name = "id")Long id) {

        SellerInfoVo infoVo = userService.querySellerById(id);
        if (infoVo == null) {
            return ResultInfoUtil.buildSuccess("卖家不存在");
        }
        return ResultInfoUtil.buildSuccess(infoVo);
    }

    /**
     * 查询个人的信息
     * @param id 卖家id
     * @return 个人信息
     */
    @ApiOperation("查询个人信息")
    @GetMapping("/self/{id}")
    public ResultInfo<SelfInfoVo> querySelf(@Min(1) @PathVariable(name = "id")Long id) {

        SelfInfoVo infoVo = userService.querySelfById(id);
        return ResultInfoUtil.buildSuccess(infoVo);
    }

    /**
     * 修改个人的信息
     * @param personalInfoForm 个人信息
     * @return 修改成功？
     */
    @ApiOperation("修改个人信息")
    @PutMapping("/self")
    public ResultInfo<String> updateSelf(@RequestBody @Valid PersonalInfoForm personalInfoForm) {

        BarterUserInfo userInfo = new BarterUserInfo();
        BeanUtils.copyProperties(personalInfoForm,userInfo,BarterUserInfo.class);
        userInfo.setUserId(Integer.toUnsignedLong(personalInfoForm.getUserId()));
        System.out.println(userInfo);
        if (userService.updateUser(userInfo) == 1) {
            return ResultInfoUtil.buildSuccess("更新成功");
        } else {
            return ResultInfoUtil.buildError("更新失败");
        }
    }



}
