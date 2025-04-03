package com.example.barter_authority.common.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * 验证码类异常
 * @author walnut
 * @version 1.0
 * @date 2021/11/7 8:18 下午
 */
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg) {
        super(msg);
    }
}

