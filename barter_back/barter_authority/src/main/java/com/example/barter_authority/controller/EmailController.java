package com.example.barter_authority.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.barter_authority.common.lang.Const;
import com.example.barter_authority.common.lang.ResultInfo;
import com.example.barter_authority.common.util.EmailUtil;
import com.example.barter_authority.common.util.RedisUtil;
import com.example.barter_authority.common.util.ResultInfoUtil;
import com.example.barter_authority.service.EmailService;
import com.example.barter_authority.service.UserService;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author walnut
 * @version 1.0
 * @className EmailController
 * @description 用来处理注册和忘记密码时的邮箱发送等功能
 * @date 2021/11/13 3:35 下午
 */
@RestController
@Api(tags = "邮箱验证相关")
@RequestMapping("/email")
public class EmailController {

    public static final String REGISTER_EMAIL = "registerEmail";
    public static final String FORGET_EMAIL = "forgetEmail";

    final
    EmailService emailService;

    @Resource
    private Producer producer;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserService userService;

    @Resource
    private EmailUtil emailUtil;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @ApiOperation("注册并校验验证码")
    @PostMapping
    public ResultInfo<String> registerEmail(@RequestBody JSONObject jsonObject) {

        String email = (String)jsonObject.get("email");
        String code = (String)jsonObject.get("code");

        //判断邮箱是否被注册
        if (!emailService.judgeEmail(email)) {
            return ResultInfoUtil.buildSuccess("此邮箱已被注册");
        }

        if (!redisUtil.hHasKey(REGISTER_EMAIL,email)) {
            return ResultInfoUtil.buildError("请发送验证码后下一步");
        }
         if(redisUtil.hget(REGISTER_EMAIL,email).equals(code)){
             
             //创建用户的信息
             if (userService.insertUserWithEmail(email) == 1){
                 redisUtil.hdel(REGISTER_EMAIL,email);
                 return ResultInfoUtil.buildSuccess("验证码无误,注册成功");
             }

             return ResultInfoUtil.buildError("系统错误:注册失败");
         }
        return ResultInfoUtil.buildError("验证码错误");

    }

    /**
     * 判断邮箱是否已被注册
     * @param email 判断邮箱
     * @return 返回json，是否被注册
     */
    @GetMapping
    @ApiOperation("注册时动态判断邮箱是否被注册")
    public ResultInfo<String> getEmail(@RequestParam(name = "email") String email) {

        if (!emailService.judgeEmail(email)) {
            return ResultInfoUtil.buildError("此邮箱已被注册");
        } else {
            return ResultInfoUtil.buildSuccess("允许注册");
        }
    }

    @GetMapping("/register/massage")
    @ApiOperation("向该邮箱发送注册邮件")
    public ResultInfo<String> sendRegisterMassage(@RequestParam(name = "email") String email){

        if (!emailService.judgeEmail(email)) {
            return ResultInfoUtil.buildSuccess("此邮箱已被注册");
        } else {
            String capTxt = producer.createText();
            try {
                emailUtil.sendMail(email,capTxt, Const.SEND_REGISTER_MAIL);
            } catch (Exception e){
                return ResultInfoUtil.buildError("系统错误：邮箱验证码发送失败");
            }
            redisUtil.hset(REGISTER_EMAIL,email,capTxt,60*5);

            return ResultInfoUtil.buildSuccess("发送成功");
        }
    }

    @GetMapping("/forget/massage")
    @ApiOperation("向该邮箱发送忘记密码邮件")
    public ResultInfo<String> sendForgetMassage(@RequestParam(name = "email") String email){

        if (emailService.judgeEmail(email)) {
            return ResultInfoUtil.buildSuccess("此邮箱未注册账号");
        } else {
            String capTxt = producer.createText();
            try {
                emailUtil.sendMail(email,capTxt, Const.SEND_FORGET_MAIL);
            } catch (Exception e){
                return ResultInfoUtil.buildError("系统错误：邮箱验证码发送失败");
            }
            redisUtil.hset(FORGET_EMAIL,email,capTxt,60*5);

            return ResultInfoUtil.buildSuccess("发送成功");
        }
    }


    @ApiOperation("校验验证码,修改密码")
    @PostMapping("/password")
    public ResultInfo<String> forgetEmail(@RequestBody JSONObject jsonObject) {

        String email = (String)jsonObject.get("email");
        String password = (String)jsonObject.get("password");
        String code = (String)jsonObject.get("code");

        //判断邮箱是否存在用户
        if (emailService.judgeEmail(email)) {
            return ResultInfoUtil.buildSuccess("此邮箱未注册账户");
        }

        if (!redisUtil.hHasKey(FORGET_EMAIL,email)) {
            return ResultInfoUtil.buildError("请发送验证码后下一步");
        }
        if(redisUtil.hget(FORGET_EMAIL,email).equals(code)){

            //更改用户的信息
            if (userService.updatePasswordByEmail(email,password) == 1){
                redisUtil.hdel(FORGET_EMAIL,email);
                return ResultInfoUtil.buildSuccess("验证码无误,密码修改成功");
            }

            return ResultInfoUtil.buildError("系统错误:更新密码失败");
        }
        return ResultInfoUtil.buildError("验证码错误");

    }
}

