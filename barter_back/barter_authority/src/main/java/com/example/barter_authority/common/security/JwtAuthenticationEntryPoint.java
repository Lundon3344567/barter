package com.example.barter_authority.common.security;

import cn.hutool.json.JSONUtil;
import com.example.barter_authority.common.enums.ResultEnum;
import com.example.barter_authority.common.lang.ResultInfo;
import com.example.barter_authority.common.util.ResultInfoUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
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
 * @className JwtAuthenticationEntryPoint
 * @description 用户未登录的拦截器，用来解决匿名用户访问无权限资源时的异常
 * @date 2021/11/11 5:46 下午
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        ResultInfo<Object> objectResultInfo = ResultInfoUtil.buildError(ResultEnum.USER_NO_ACCESS,"未登录");

        outputStream.write(JSONUtil.toJsonStr(objectResultInfo).getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();
    }
}

