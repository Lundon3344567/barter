package com.example.barter_authority.mapper;

import com.example.barter_authority.domain.entity.BarterUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (BarterUserInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-09 13:34:30
 */
@Mapper
public interface BarterUserInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    BarterUserInfo queryById(Long userId);

    int queryByUserId(Long userId);

    /**
     * 查询指定行数据
     *
     * @param barterUserInfo 查询条件
     * @param pageable       分页对象
     * @return 对象列表
     */
    List<BarterUserInfo> queryAllByLimit(BarterUserInfo barterUserInfo, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param barterUserInfo 查询条件
     * @return 总行数
     */
    long count(BarterUserInfo barterUserInfo);

    /**
     * 新增数据
     *
     * @param barterUserInfo 实例对象
     * @return 影响行数
     */
    int insert(BarterUserInfo barterUserInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterUserInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BarterUserInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterUserInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BarterUserInfo> entities);

    /**
     * 修改数据
     *
     * @param barterUserInfo 实例对象
     * @return 影响行数
     */
    int update(BarterUserInfo barterUserInfo);

    /**
     * 通过email修改数据（注册用）
     *
     * @param barterUserInfo 注册的信息
     * @return 影响行数
     */
    int updateByEmail(BarterUserInfo barterUserInfo);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Long userId);

    /**
     * 通过名字查询
     *
     * @param username 用户名
     * @return 单个用户的列表
     */
    List<BarterUserInfo> queryByName(String username);

    /**
     * 查询所有的用户
     *
     * @return 所有用户
     */
    List<BarterUserInfo> queryAll();

    /**
     * 通过邮箱查询用户数量
     *
     * @param email 邮箱
     * @return 存在数量
     */
    int countByEmail(String email);

    /**
     * 通过username查询用户数量
     *
     * @param username 用户名
     * @return 存在数量
     */
    int countByUsername(String username);

}

