package com.example.barter_authority.service.impl;

import com.example.barter_authority.mapper.BarterUserInfoMapper;
import com.example.barter_authority.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author walnut
 * @version 1.0
 * @className EmailServiceImpl
 * @description TODO
 * @date 2021/11/13 3:41 下午
 */
@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    BarterUserInfoMapper userInfoMapper;

    @Override
    public boolean judgeEmail(String email) {
        return userInfoMapper.countByEmail(email) == 0;
    }
}

