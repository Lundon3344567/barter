package com.example.barter_authority.service.impl;

import com.example.barter_authority.common.lang.Const;
import com.example.barter_authority.common.util.*;
import com.example.barter_authority.domain.entity.BarterPermissionInfo;
import com.example.barter_authority.domain.entity.BarterRoleInfo;
import com.example.barter_authority.domain.entity.BarterUserInfo;
import com.example.barter_authority.domain.entity.BarterUserRole;
import com.example.barter_authority.domain.form.PageForm;
import com.example.barter_authority.domain.form.RegisterForm;
import com.example.barter_authority.domain.form.UserSearchForm;
import com.example.barter_authority.domain.form.UserStateForm;
import com.example.barter_authority.domain.vo.BarterUserInfoVo;
import com.example.barter_authority.domain.vo.SelfInfoVo;
import com.example.barter_authority.domain.vo.SellerInfoVo;
import com.example.barter_authority.domain.vo.UserVo;
import com.example.barter_authority.mapper.BarterPermissionInfoMapper;
import com.example.barter_authority.mapper.BarterUserInfoMapper;
import com.example.barter_authority.mapper.BarterUserRoleMapper;
import com.example.barter_authority.service.RoleService;
import com.example.barter_authority.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.example.barter_authority.common.lang.*;

import static com.example.barter_authority.common.lang.Const.*;
import static com.example.barter_authority.common.util.CompareUtil.getDifference;


/**
 * @author walnut
 * @version 1.0
 * @date 2021/11/9 1:14 上午
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String USER_LIST = "UserList";
    public static final String AUTH_INFO = "GrantedAuthority:";

    @Autowired
    private BarterUserInfoMapper userInfoMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BarterPermissionInfoMapper permissionInfoMapper;

    @Autowired
    private BarterUserRoleMapper userRoleMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FlashUtil flashUtil;

    /**
     * 注册用户
     * 需要开启事务
     *
     * @param registerForm 需要注册的用户
     * @return 注册结果码
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int userRegister(RegisterForm registerForm) {

        if (!userInfoMapper.queryByName(registerForm.getUsername()).isEmpty()) {
            return USER_EXIST;
        }
        BarterUserInfo userInfo = new BarterUserInfo();

        BeanUtils.copyProperties(registerForm, userInfo, BarterUserInfo.class);

        //设置注册信息
        userInfo.setLastEditTime(new Date());
        userInfo.setPassword(bCryptPasswordEncoder().encode(userInfo.getPassword()));

        System.out.println(userInfo);
        int effectLine = userInfoMapper.updateByEmail(userInfo);

        //重置存储的用户信息
        redisUtil.del(USER_LIST);

        if (effectLine != 0) {
            //插入用户权限表，赋予普通用户权限
            if (roleService.addNormalUserRole(userInfo.getEmail()) != 1) {
                //发成错误，事务回滚
                throw new RuntimeException("插入user_role表失败");
            }
            return OK;
        } else {
            return FILED;
        }

    }

    @Override
    public BarterUserInfo queryUserById(Long id) {
        return userInfoMapper.queryById(id);
    }


    @Override
    public PageInfo<BarterUserInfo> queryUserByPage(PageForm pageForm) {


        PageHelper.startPage(pageForm.getRequestPage(), pageForm.getPageSize());

        //查询整体
        List<BarterUserInfo> barterUserInfos = userInfoMapper.queryAll();

        return new PageInfo<>(barterUserInfos);
    }

    @Override
    public int deleteUserById(Long id) {

        //重置存储的用户信息
        redisUtil.del(USER_LIST);

        return userInfoMapper.deleteById(id);
    }

    @Override
    public int updateUser(BarterUserInfo userInfo) {
        //重置存储的用户信息
        redisUtil.del(USER_LIST);

        //刷新数据
        flashUtil.flash();
        redisUtil.del("MenuTree");

        userInfo.setLastEditTime(new Date());
        return userInfoMapper.update(userInfo);
    }

    @Override
    public int insertUser(BarterUserInfo userInfo) {

        //重置存储的用户信息
        redisUtil.del(USER_LIST);
        //刷新数据
        flashUtil.flash();
        redisUtil.del("MenuTree");

        return userInfoMapper.insert(userInfo);
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        String authority = "";

        String username = userInfoMapper.queryById(userId).getUsername();
        //读取缓存是否有对应的权限信息
        if (redisUtil.hasKey(AUTH_INFO + username)) {
            authority = (String) redisUtil.get(AUTH_INFO + username);
        }
        //没有缓存的情况下：
        else {
            List<BarterRoleInfo> barterRoleInfos = roleService.queryRoleByUserId(userId);
            //获取角色的编码
            if (barterRoleInfos.size() > 0) {
                String roleInfo = barterRoleInfos.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
                authority = roleInfo + ",";
            }
            //TODO
            //获取菜单的编码
 
            List<Long> menuIds = permissionInfoMapper.getNavMenuIds(userId);
            if (menuIds.size() > 0) {
                List<BarterPermissionInfo> barterPermissionInfos = permissionInfoMapper.queryByIds(menuIds);
                String menuPerms = barterPermissionInfos.stream().map(BarterPermissionInfo::getPerms).collect(Collectors.joining(","));

                authority = authority.concat(menuPerms);
            }
            //redis存储一小时
            redisUtil.set(AUTH_INFO + username, authority, DEFAULT_EXPIRATION_TIME);
            flashUtil.add(AUTH_INFO + username);
        }
        return authority;


    }

    @Override
    public long queryIdByUsername(String username) {
        return userInfoMapper.queryByName(username).get(0).getUserId();
    }

    @Override
    public int insertUserWithEmail(String email) {
        BarterUserInfo userInfo = new BarterUserInfo();

        //组装userInfo的信息
        userInfo.setUserState(USER_ON_USE);
        userInfo.setCreateTime(new Date());
        userInfo.setLastEditTime(new Date());
        userInfo.setEmail(email);

        //重置存储的用户信息
        redisUtil.del(USER_LIST);
        //刷新数据
        flashUtil.flash();
        redisUtil.del("MenuTree");

        return userInfoMapper.insert(userInfo);
    }

    @Override
    public boolean judgeUsernameExist(String username) {
        return userInfoMapper.countByUsername(username) != 0;
    }

    @Override
    public BarterUserInfoVo queryMainInfoByUsername(String username) {
        BarterUserInfo barterUserInfo = userInfoMapper.queryByName(username).get(0);
        BarterUserInfoVo userInfoVo = new BarterUserInfoVo();
        BeanUtils.copyProperties(barterUserInfo, userInfoVo, BarterUserInfoVo.class);
        return userInfoVo;
    }

    @Override
    public BarterUserInfo queryUserByUsername(String username) {
        return userInfoMapper.queryByName(username).get(0);
    }

    @Override
    public SellerInfoVo querySellerById(Long id) {
        BarterUserInfo userInfo = userInfoMapper.queryById(id);
        if (userInfo == null) {
            return null;
        }
        SellerInfoVo infoVo = new SellerInfoVo();
        BeanUtils.copyProperties(userInfo,infoVo,SellerInfoVo.class);
        return infoVo;
    }

    @Override
    public SelfInfoVo querySelfById(Long id) {
        BarterUserInfo userInfo = userInfoMapper.queryById(id);
        if (userInfo == null) {
            return null;
        }
        SelfInfoVo infoVo = new SelfInfoVo();
        BeanUtils.copyProperties(userInfo,infoVo,SelfInfoVo.class);
        return infoVo;
    }

    @Override
    public int updatePasswordByEmail(String email, String password) {

        BarterUserInfo userInfo = new BarterUserInfo();
        System.out.println(password);
        userInfo.setPassword(bCryptPasswordEncoder().encode(password));
        userInfo.setEmail(email);
        userInfo.setLastEditTime(new Date());
        //重置存储的用户信息
        redisUtil.del(USER_LIST);
        //刷新数据
        flashUtil.flash();
        redisUtil.del("MenuTree");

        return userInfoMapper.updateByEmail(userInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserRoleByRoleId(Long userId,List<Integer> roleIds) throws Exception {

        //判断用户是否有资格更新角色信息
        if (userInfoMapper.queryById(userId).getUserState() < Const.USER_LOG_OFF) {
            return FILED;
        }

        List<Integer> oldRoleId = userRoleMapper.queryAllRoleIdByUserId(userId);

        //获取需要删除的角色id
        List<Integer> deleteRole = getDifference(oldRoleId, roleIds);

        //获取需要添加的角色id
        List<Integer> addRole = getDifference(roleIds, oldRoleId);

        //重置存储的用户信息
        redisUtil.del(USER_LIST);
        //需要事务支持
        //添加需要增加的角色
        for (Integer roleId : addRole) {
            if(userRoleMapper.insert(new BarterUserRole(null,userId,roleId.longValue())) != 1) {
                //发成错误，事务回滚
                throw new Exception("增加角色失败");
            }

        }

        //删除需要删除的角色
        for (Integer roleId : deleteRole) {
            if (userRoleMapper.deleteByRecord(userId,roleId.longValue()) != 1) {
                //发成错误，事务回滚
                throw new Exception("删除角色失败");
            }
        }
        //刷新数据
        flashUtil.flash();
        redisUtil.del("MenuTree");
        return OK;
    }

    @Override
    public int changeUserState(UserStateForm userStateForm) {
        BarterUserInfo barterUserInfo = new BarterUserInfo();
        barterUserInfo.setUserState(userStateForm.getState());
        barterUserInfo.setUserId(userStateForm.getUserId().longValue());
        barterUserInfo.setLastEditTime(new Date());

        //重置存储的用户信息
        redisUtil.del(USER_LIST);
        //刷新数据
        flashUtil.flash();
        redisUtil.del("MenuTree");

        return userInfoMapper.update(barterUserInfo);
    }

    @Override
    public PageInfo<UserVo> queryUserByCondition(UserSearchForm userSearchForm) {

        PageForm pageForm = new PageForm(userSearchForm.getRequestPage(), userSearchForm.getPageSize());

        List<BarterUserInfo> barterUserInfos;
        //存在redis缓存的情况
        if (redisUtil.hasKey(USER_LIST)) {
            barterUserInfos = (List<BarterUserInfo>)redisUtil.get(USER_LIST);
        }
        else {
            barterUserInfos = userInfoMapper.queryAll();
            //放入redis中缓存
            redisUtil.set(USER_LIST,barterUserInfos,DEFAULT_EXPIRATION_TIME);
        }

        //搜索匹配信息
        String namePattern,telPattern;
        Integer state = userSearchForm.getState();
        //如果用户名非空，则判断用户名
        String name = userSearchForm.getName();
        if (name != null) {
            namePattern = ".*"+ PatternUtil.escapeExprSpecialWord(name)+".*";
            barterUserInfos.removeIf(userInfo -> !Pattern.matches(namePattern,userInfo.getUsername()== null?"":userInfo.getUsername()));
        }
        //如果电话非空，则判断电话
        String tel = userSearchForm.getTel();
        if (tel != null) {
            telPattern = ".*"+PatternUtil.escapeExprSpecialWord(tel)+".*";
            barterUserInfos.removeIf(userInfo -> !Pattern.matches(telPattern,userInfo.getUserPhone()== null?"":userInfo.getUserPhone()));
        }
        //如果用户状态非空，则判断用户状态
        if (state != null) {
            barterUserInfos.removeIf(userInfo -> !state.equals(userInfo.getUserState()));
        }
        //转换并返回
        //通过工具类拷贝
        List<UserVo> userVos = CopyUtil.copyList(barterUserInfos, UserVo.class);
        //通过工具类分页
        //TODO
        return PageUtil.pageList(pageForm, userVos);
    }


}

