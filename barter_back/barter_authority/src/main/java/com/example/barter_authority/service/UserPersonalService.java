package com.example.barter_authority.service;


import com.example.barter_authority.domain.form.PageForm;
import com.example.barter_authority.domain.vo.UserPersonalInfoVo;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserPersonalService
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-24 16:39
 * @Version 1.0
 **/
@Service
public interface UserPersonalService {

    /**
     * 通过用户Id查询用户个人基础信息
     * @param userId
     * @return
     */
    UserPersonalInfoVo queryUserPersonalInfoByUserId(long userId);


}
