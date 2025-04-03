package com.example.barter_log.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.barter_authority.common.util.JsonParamUtil;
import com.example.barter_log.common.exception.CaptchaException;
import com.example.barter_log.common.lang.Const;
import com.example.barter_log.common.security.LoginFailureHandler;
import com.example.barter_log.common.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 验证码校验的过滤器，在用户名密码校验之前
 * @author walnut
 * @version 1.0
 * @date 2021/11/7 8:08 下午
 */
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CaptchaFilter.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {



        String url = request.getRequestURI();
        LOG.info("请求接口: "+request.getMethod().toUpperCase() +" : "+ url);

        if ("/captcha".equals(url) && "POST".equals(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers","*");
            response.setHeader("Access-Control-Allow-Methods","*");
            response.setHeader("Access-Control-Allow-Credentials","true");
            response.setHeader("Access-Control-Max-Age","3600");
        }
        boolean login = "/login".equals(url) && "POST".equals(request.getMethod());
        boolean register = "/user/register".equals(url) && "POST".equals(request.getMethod());
        if ( login || register ) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers","*");
            response.setHeader("Access-Control-Allow-Methods","*");
            response.setHeader("Access-Control-Allow-Credentials","true");
            response.setHeader("Access-Control-Max-Age","3600");
            try {
                // 校验验证码
                LOG.info( "LOG==> " + String.format("%-12s","null") + String.format("%-12s",null)  + " :: info: 验证码校验");
                validate(request);
            } catch (CaptchaException e) {
                // 跳转到认证失败处理器
                LOG.info( "LOG==> " + String.format("%-12s","e.getMessage()") + String.format("%-12s",e.getMessage())  + " :: info: errToken");
                loginFailureHandler.onAuthenticationFailure(request, response, e);
//                throw e;
                return;
            }

        }
        // 继续执行Filter
        filterChain.doFilter(request, response);
    }

    /**
     * 校验验证码
     *
     * @param request
     */
    private void validate(HttpServletRequest request) throws CaptchaException {

        LOG.info( "LOG==> " + String.format("%-12s","session:") + String.format("%-12s",request.getSession(false))  + " :: info: sessionINFO IN CaptchaFilter");
        String input = null;
        if ("/user/register".equals(request.getRequestURI()) && "POST".equals(request.getMethod())) {
            String jsonParam = JsonParamUtil.getJsonParam(request);
            System.out.println(jsonParam);
            input = JSONObject.parseObject(jsonParam).getString("code");
            LOG.info( "LOG==> " + String.format("%-12s","input") + String.format("%-12s",input)  + " :: info: inputCode");
            request.getSession().setAttribute("form",jsonParam);

        } else {
            input = request.getParameter("code");
        }
        //微信小程序接口
        if (input.equals(Const.CODE)){
            return;
        }
        Enumeration<String> parameterNames = request.getParameterNames();
        LOG.info( "LOG==> " + String.format("%-12s","parameterNames") + String.format("%-12s",parameterNames)  + " :: info: parameterNames");

        //获取系统中的验证码
        String code = (String) request.getSession().getAttribute(Const.CAPTCHA_KEY);

        //获取系统中验证码创建时间
        Long time = (Long) request.getSession().getAttribute(Const.CAPTCHA_KEY+"Time" );

        request.getSession().removeAttribute(Const.CAPTCHA_KEY);
        request.getSession().removeAttribute(Const.CAPTCHA_KEY+"Time");

        if (code == null || time-System.currentTimeMillis() >= 3*60*1000 || !code.equals(input)) {

            //TODO 测试，需删除LOG
            if (code == null) {
                LOG.info( "LOG==> " + String.format("%-12s","null") + String.format("%-12s",code == null)  + " :: info: NotExist");
            } else{
                LOG.info( "LOG==> " + String.format("%-12s","codeErr") + String.format("%-12s",!code.equals(input))  + " :: info: errCode");
                LOG.info( "LOG==> " + String.format("%-12s","code") + String.format("%-12s",code)  + " :: info: errCode");
                LOG.info( "LOG==> " + String.format("%-12s","input") + String.format("%-12s",input)  + " :: info: errCode");
                LOG.info( "LOG==> " + String.format("%-12s","time") + String.format("%-12s",time-System.currentTimeMillis() >= 3*60*1000)  + " :: info: errTime");
            }
            throw new CaptchaException("验证码错误,请刷新验证码");
        }

//        Object code = request.getAttribute(Const.CAPTCHA_KEY);

//        if (!code.equals(redisUtil.hget(Const.CAPTCHA_KEY, key))) {
//            System.out.println(redisUtil.hget(Const.CAPTCHA_KEY, key));
//            throw new CaptchaException("验证码错误");
//        }

    }

}

