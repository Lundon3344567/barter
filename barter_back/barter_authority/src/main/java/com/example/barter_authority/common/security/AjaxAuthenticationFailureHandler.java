package com.example.barter_authority.common.security;

import com.alibaba.fastjson.JSON;
import com.example.barter_authority.common.enums.ResultEnum;
import com.example.barter_authority.common.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author walnut
 * @Date 2021/11/6 4:25 下午
 * @Version 1.0
 */
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {
private static final Logger LOG = LoggerFactory.getLogger(AjaxAuthenticationFailureHandler.class);
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        LOG.info( "LOG==> " + String.format("%-12s","null") + String.format("%-12s",null)  + " :: info: false");
        httpServletResponse.getWriter().write(JSON.toJSONString(ResultVo.result(ResultEnum.USER_LOGIN_FAILED, false)));
    }

}