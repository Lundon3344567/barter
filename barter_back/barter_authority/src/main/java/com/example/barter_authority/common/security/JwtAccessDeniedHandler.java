package com.example.barter_authority.common.security;

import cn.hutool.json.JSONUtil;
import com.example.barter_authority.common.enums.ResultEnum;
import com.example.barter_authority.common.lang.ResultInfo;
import com.example.barter_authority.common.util.ResultInfoUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
 * @className AccessDeniedHandler
 * @description 权限不足的拦截器，用来解决认证过的用户访问无权限资源时的异常
 * @date 2021/11/11 5:43 下午
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOG = LoggerFactory.getLogger(JwtAccessDeniedHandler.class);


    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        LOG.info( "LOG==> " + String.format("%-12s","null") + String.format("%-12s",null)  + " :: info: 权限不足");
        ResultInfo<Object> objectResultInfo = ResultInfoUtil.buildError(ResultEnum.ACCESS_DENIED,"用户权限不足");

        outputStream.write(JSONUtil.toJsonStr(objectResultInfo).getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();
    }
}

