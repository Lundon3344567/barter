package com.example.barter_log.service.impl;


import com.example.barter_authority.domain.entity.BarterUserDetails;
import com.example.barter_authority.domain.entity.BarterUserInfo;
import com.example.barter_authority.mapper.BarterUserInfoMapper;
import com.example.barter_authority.service.UserService;
import com.example.barter_log.common.annotation.DoLog;
import com.example.barter_log.common.lang.Const;
import com.example.barter_log.common.lang.LogConstants;
import com.example.barter_log.common.lang.OperateTypeContains;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * MyUserDetailService通过数据库实现登录业务（身份校验）
 * @author walnut
 * @date 2021/11/6 5:15 下午
 * @version 1.0
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(MyUserDetailServiceImpl.class);

    @Autowired
    private BarterUserInfoMapper barterUserInfoMapper;

    @Autowired
    private UserService userService;



    @Override
    @DoLog(operateModule = "LOGIN",operateType = OperateTypeContains.OPERATE_LOGIN, operateDesc = "登录", requestStyle = LogConstants.REQUEST_COMMON_STYLE)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过数据库查询用户名是否存在
        //创建查询规则
        BarterUserInfo barterUserInfo = new BarterUserInfo();
        barterUserInfo.setUsername(username);

        //TODO 优化
        List<BarterUserInfo> barterUserInfos = barterUserInfoMapper.queryByName(username);
        LOG.info( "LOG==> " + String.format("%-12s","barterUserInfos") + String.format("%-12s",barterUserInfos)  + " :: info: 查询用户数据");

        //数据库没有用户名，认证失败
        if (barterUserInfos.isEmpty()){
            LOG.info("用户不存在");
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        if (barterUserInfos.get(0).getUserState() != Const.USER_ON_USE && barterUserInfos.get(0).getUserState() != Const.ADMIN_ON_USE){
            System.out.println("账号不可用");
            throw new UsernameNotFoundException("账户不可用");
        }
        BarterUserInfo userInfo = barterUserInfos.get(0);
        return new BarterUserDetails(userInfo.getUserId(), userInfo.getUsername(), userInfo
        .getPassword(),getUserAuthority(userInfo.getUserId()));
    }

    /**
     * 获取用户权限信息(角色及菜单权限)
     * @param userId 用户id
     * @return 权限列表
     */
    public Collection<? extends GrantedAuthority> getUserAuthority(Long userId) {
        String authority = userService.getUserAuthorityInfo(userId);

        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
