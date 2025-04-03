package com.example.barter_log.common.security;

import cn.hutool.json.JSONUtil;
import com.example.barter_log.common.lang.Const;
import com.example.barter_log.common.lang.ResultInfo;
import com.example.barter_log.common.util.JwtUtil;
import com.example.barter_log.common.util.ResultInfoUtil;
import com.example.barter_authority.domain.vo.BarterUserInfoVo;
import com.example.barter_authority.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author walnut
 * @version 1.0
 * @date 2021/11/7 10:00 下午
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        //生成jwt，放置到请求头
        LOG.info("LOG==> " + String.format("%-12s", "Const.OK") + String.format("%-12s", Const.OK) + " :: info: 生成jwt，放置到请求头");
        String name = authentication.getName();

        long id = userService.queryIdByUsername(name);
        BarterUserInfoVo userInfoVo = userService.queryMainInfoByUsername(name);

        String jwt = JwtUtil.sign(userInfoVo.getUserId(), name);

        httpServletResponse.setHeader(JwtUtil.getHeader(), jwt);

        LOG.info("LOG==> " + String.format("%-12s", "jwt") + String.format("%-12s",jwt) + " :: info: 登录成功");
        ResultInfo<Object> objectResultInfo = ResultInfoUtil.buildSuccess(userInfoVo);

        outputStream.write(JSONUtil.toJsonStr(objectResultInfo).getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();
    }
}

