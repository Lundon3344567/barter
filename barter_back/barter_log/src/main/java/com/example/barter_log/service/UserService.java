package com.example.barter_log.service;

import com.example.barter_authority.domain.entity.BarterUserInfo;
import com.example.barter_authority.domain.form.PageForm;
import com.example.barter_authority.domain.form.RegisterForm;
import com.example.barter_authority.domain.form.UserSearchForm;
import com.example.barter_authority.domain.form.UserStateForm;
import com.example.barter_authority.domain.vo.BarterUserInfoVo;
import com.example.barter_authority.domain.vo.SelfInfoVo;
import com.example.barter_authority.domain.vo.SellerInfoVo;
import com.example.barter_authority.domain.vo.UserVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务
 *
 * @author walnut
 * @version 1.0
 * @date 2021/11/9 1:14 上午
 */
@Service
public interface UserService {
    /**
     * 用户注册业务逻辑
     *
     * @param registerForm 需要注册的用户
     * @return 200:注册成功 401:用户存在
     */
    int userRegister(RegisterForm registerForm);

    /**
     * 通过id查询单个用户的信息
     *
     * @param id 需要查询人的id
     * @return 单个或0个查询到的BarterUserInfo
     */
    BarterUserInfo queryUserById(Long id);

    /**
     * 分页查询用户信息
     *
     * @param pageForm 自定义接收前端传来的分页信息，具体见controller层
     * @return PageInfo pageHelper 自带的分页信息数据
     */
    PageInfo<BarterUserInfo> queryUserByPage(PageForm pageForm);

    /**
     * 通过id删除用户
     *
     * @param id id
     * @return 影响行数
     */
    int deleteUserById(Long id);

    /**
     * 通过id更新用户
     *
     * @param userInfo 用户信息
     * @return 影响行数
     */
    int updateUser(BarterUserInfo userInfo);

    /**
     * 通过id插入用户
     *
     * @param userInfo 用户信息
     * @return 影响行数
     */
    int insertUser(BarterUserInfo userInfo);

    /**
     * 获取用户的角色和权限信息
     *
     * @param userId 待查用户
     * @return 用户的角色和权限信息
     */
    String getUserAuthorityInfo(Long userId);

    /**
     * 通过用户名查询id
     *
     * @param username 用户名
     * @return id
     */
    long queryIdByUsername(String username);

    /**
     * 注册用户时通过邮箱注册
     *
     * @param email 邮箱
     * @return 影响行数
     */
    int insertUserWithEmail(String email);

    /**
     * 判断用户名是否存在
     *
     * @param username 待判断用户名
     * @return 存在否 若存在返回True，不存在 返回False
     */
    boolean judgeUsernameExist(String username);

    /**
     * 通过username查询 用户基础信息
     *
     * @param username 用户名
     * @return 用户基础信息
     */
    BarterUserInfoVo queryMainInfoByUsername(String username);

    /**
     * 通过username 查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    BarterUserInfo queryUserByUsername(String username);

    /**
     * 通过id查询卖家信息
     *
     * @param id 卖家id
     * @return 卖家信息
     */
    SellerInfoVo querySellerById(Long id);

    /**
     * 通过id查询个人信息
     *
     * @param id 个人id
     * @return 个人信息
     */
    SelfInfoVo querySelfById(Long id);

    /**
     * 更新密码
     *
     * @param email    邮箱
     * @param password 密码
     * @return 更新成功1，更新失败0
     */
    int updatePasswordByEmail(String email, String password);

    /**
     * 更新角色
     *
     * @param userId  用户id
     * @param roleIds 角色id
     * @return Const的代码
     */
    int updateUserRoleByRoleId(Long userId, List<Integer> roleIds) throws Exception;

    /**
     * 更改用户状态
     *
     * @param userStateForm 更新信息
     * @return 更改成功 1 更改失败 0
     */
    int changeUserState(UserStateForm userStateForm);

    /**
     * 通过条件查询用户信息
     *
     * @param userSearchForm 条件信息
     * @return 返回查询到的用户
     */
    PageInfo<UserVo> queryUserByCondition(UserSearchForm userSearchForm);
}
