package com.example.barter_authority.service.impl;

import com.example.barter_authority.domain.entity.BarterUserInfo;
import com.example.barter_authority.domain.form.PageForm;
import com.example.barter_authority.domain.vo.UserPersonalInfoVo;
import com.example.barter_authority.mapper.BarterUserInfoMapper;
import com.example.barter_authority.service.UserPersonalService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @ClassName UserPersonalServiceImpl
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-24 16:40
 * @Version 1.0
 **/
@Service
public class UserPersonalServiceImpl implements UserPersonalService {

    @Autowired
    BarterUserInfoMapper barterUserInfoMapper;


    @Override
    public UserPersonalInfoVo queryUserPersonalInfoByUserId(long userId) {
        BarterUserInfo barterUserInfo = barterUserInfoMapper.queryById(userId);
        UserPersonalInfoVo userPersonalInfoVo = new UserPersonalInfoVo();
        BeanUtils.copyProperties(barterUserInfo,userPersonalInfoVo,UserPersonalInfoVo.class);
        return userPersonalInfoVo;
    }

}
