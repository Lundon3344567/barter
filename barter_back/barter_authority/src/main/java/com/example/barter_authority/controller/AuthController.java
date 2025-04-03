package com.example.barter_authority.controller;

import com.example.barter_authority.common.lang.Const;
import com.example.barter_authority.common.util.RedisUtil;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码接口，用户获取验证码相关信息返回前端并放入redis，便于登录时验证
 *
 * @author walnut
 * @version 1.0
 * @date 2021/11/7 2:10 下午
 */
@Controller
@Api(tags = "认证管理相关")
@CrossOrigin
public class AuthController {

    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation("验证码获取接口")
    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {

        //设置文本内容
        response.setContentType("image/jpeg");
        //创建验证码文本
        String capTxt = producer.createText();

        //将验证码文本设置到session中
        request.getSession().setAttribute(Const.CAPTCHA_KEY, capTxt);
        request.getSession().setAttribute(Const.CAPTCHA_KEY + "Time", System.currentTimeMillis());

        //创建验证码图片
        BufferedImage image = producer.createImage(capTxt);

        //获取响应输出流
        ServletOutputStream out = response.getOutputStream();

        //将验证码数据写到响应输出流中
        try (out) {
            ImageIO.write(image, "jpg", out);
            out.flush();
        }
    }
}
