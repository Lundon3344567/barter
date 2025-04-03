package com.example.barter_log.common.security;

import cn.hutool.json.JSONUtil;
import com.example.barter_log.common.enums.ResultEnum;
import com.example.barter_log.common.lang.ResultInfo;
import com.example.barter_log.common.util.JwtUtil;
import com.example.barter_log.common.util.ResultInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
 * @className MyLogoutSuccessHandler
 * @description 登出操作Handler
 * @date 2021/11/15 2:47 下午
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MyLogoutSuccessHandler.class);
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        response.setHeader(JwtUtil.getHeader(), "");

        ResultInfo<Object> logOut = ResultInfoUtil.buildSuccess("登出成功");

        outputStream.write(JSONUtil.toJsonStr(logOut).getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();
    }

}

