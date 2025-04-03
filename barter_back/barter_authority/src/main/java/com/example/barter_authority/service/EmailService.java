package com.example.barter_authority.service;

/**
 * @author walnut
 * @version 1.0
 * @className EmailService
 * @description TODO
 * @date 2021/11/13 3:41 下午
 */
public interface EmailService {

    /**
     * 判断该邮箱是否被注册
     * @param email
     * @return
     */
    boolean judgeEmail(String email);
}
