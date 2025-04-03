package com.example.barter_authority.config;


import com.example.barter_authority.common.security.*;
import com.example.barter_authority.interceptor.CaptchaFilter;
import com.example.barter_authority.interceptor.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring security配置类
 *
 * @Author walnut
 * @Date 2021/10/26 10:16 上午
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 白名单
     */
    private static final String[] URL_WHITELIST = {
            "/login",
            "/logout",
            "/captcha",
            "/qiniu/image",
            "/adadmin/ad/page",
            "/adadmin/ad/pages",
            "/order/**",
            "/view/**",
            "/released/classify/**",
            "/favicon.ico",
            "/swagger-ui.html",
            "/v2/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/user/register",
            "/email/**",
            "/user",
            "/druid/**",
            "/admin/goods/**",
            "/goods/**",
            "/druid/**",
            "/hello",
            "/ws/**",
            "/websocket/**",
            "/message/**"
    };

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CaptchaFilter captchaFilter;

    @Autowired
    MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private JwtAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;


    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }


    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                //登录配置
                .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                //登出配置
                .and()
                .logout()
                .logoutSuccessHandler(myLogoutSuccessHandler)
                //禁用session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                //配置拦截规则
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()
                .anyRequest().authenticated()

                //配置异常处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)

                //配置自定义过滤器
                .and()
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(jwtAuthenticationFilter())


        ;
    }


}
