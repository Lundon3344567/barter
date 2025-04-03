package com.example.barter_authority.common.security;

import cn.hutool.json.JSONUtil;
import com.example.barter_authority.common.enums.ResultEnum;
import com.example.barter_authority.common.lang.ResultInfo;
import com.example.barter_authority.common.util.ResultInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
 * @date 2021/11/7 9:04 下午
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LoginFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        LOG.info( "LOG==> " + String.format("%-12s","null") + String.format("%-12s",null)  + " :: info: 用户名密码错误");

        ResultInfo<Object> objectResultInfo = ResultInfoUtil.buildError(ResultEnum.USER_LOGIN_FAILED,e.getMessage());

        outputStream.write(JSONUtil.toJsonStr(objectResultInfo).getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();
    }
}

