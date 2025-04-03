package com.example.barter_authority.mapper;

import com.example.barter_authority.domain.entity.BarterRoleInfo;
import com.example.barter_authority.domain.entity.BarterUserInfo;
import com.example.barter_authority.domain.entity.BarterUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (BarterUserRole)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-09 13:34:30
 */
@Mapper
public interface BarterUserRoleMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    BarterUserRole queryById(Long id);

    /**
     * 通过id 获取所有的角色
     * @param id 用户id
     * @return 拥有的角色
     */
    List<BarterUserRole> queryAllById(Long id);

    /**
     * 查询指定行数据
     *
     * @param barterUserRole 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<BarterUserRole> queryAllByLimit(BarterUserRole barterUserRole, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param barterUserRole 查询条件
     * @return 总行数
     */
    long count(BarterUserRole barterUserRole);

    /**
     * 新增数据
     *
     * @param barterUserRole 实例对象
     * @return 影响行数
     */
    int insert(BarterUserRole barterUserRole);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterUserRole> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BarterUserRole> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterUserRole> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BarterUserRole> entities);

    /**
     * 修改数据
     *
     * @param barterUserRole 实例对象
     * @return 影响行数
     */
    int update(BarterUserRole barterUserRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 通过用户id 查询所有角色信息
     *
     * @param userId 角色id
     * @return 角色信息
     */
    List<BarterRoleInfo> queryAllRoleByUserId(Long userId);

    /**
     * 通过email创建普通用户
     *
     * @param email 用户注册的
     * @return 影响行数
     */
    int insertNormalUserByEmail(String email);

    /**
     * 更新用户的角色信息
     * @param userId 用户id
     * @return 影响行数
     */
    List<Integer> queryAllRoleIdByUserId(Long userId);

    /**
     * 通过记录删除用户拥有的角色
     * @param userId 用户id
     * @param roleId 角色id
     * @return 影响行数
     */
    int deleteByRecord(Long userId, long roleId);

    /**
     * 通过角色id查询拥有角色的人
     *
     * @param id 角色id
     * @return 角色拥有人数量
     */
    int queryUserNumByRoleId(Long id);

    /**
     * 通过角色id查询拥有角色的人员列表
     * @param roleId 角色id
     * @return 角色列表
     */
    List<BarterUserInfo> queryUserByRoleId(Long roleId);
}

