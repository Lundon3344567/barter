package com.example.barter_authority.interceptor;

import cn.hutool.core.util.StrUtil;
import com.example.barter_authority.common.util.JwtUtil;
import com.example.barter_authority.common.util.RespUtil;
import com.example.barter_authority.service.impl.MyUserDetailServiceImpl;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录过滤器，校验token
 * @author walnut
 * @version 1.0
 * @date 2021/11/8 5:12 下午
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private MyUserDetailServiceImpl userDetailService;

    @Autowired
    private JwtUtil jwtUtil;




    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(JwtUtil.getHeader());



        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
//            return;

        }
        //判断token的正确性
        try {
            JwtUtil.verify(jwt);
        }catch (JwtException e){
            RespUtil.responseErr("token不合法",response);
            return;
        }
        
        String userName = JwtUtil.getUserName(jwt);
        Long userId = JwtUtil.getUserId(jwt);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userName, null, userDetailService.getUserAuthority(userId));

        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);
    }
}

