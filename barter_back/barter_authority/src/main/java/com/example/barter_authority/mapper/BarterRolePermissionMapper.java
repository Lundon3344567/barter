package com.example.barter_authority.mapper;

import com.example.barter_authority.domain.entity.BarterPermissionInfo;
import com.example.barter_authority.domain.entity.BarterRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (BarterRolePermission)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-09 13:35:33
 */
@Mapper
public interface BarterRolePermissionMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    BarterRolePermission queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param barterRolePermission 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<BarterRolePermission> queryAllByLimit(BarterRolePermission barterRolePermission, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param barterRolePermission 查询条件
     * @return 总行数
     */
    long count(BarterRolePermission barterRolePermission);

    /**
     * 新增数据
     *
     * @param barterRolePermission 实例对象
     * @return 影响行数
     */
    int insert(BarterRolePermission barterRolePermission);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterRolePermission> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BarterRolePermission> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterRolePermission> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BarterRolePermission> entities);

    /**
     * 修改数据
     *
     * @param barterRolePermission 实例对象
     * @return 影响行数
     */
    int update(BarterRolePermission barterRolePermission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 通过记录删除
     * @param barterRolePermission 记录
     * @return 删除成功？
     */
    int deleteByItem(BarterRolePermission barterRolePermission);

    /**
     * 通过permissionId查询权限拥有人数
     * @param permissionId 权限id
     * @return 拥有权限的人数
     */
    int countByPermissionId(Long permissionId);

    /**
     * 通过roleId查询所有角色拥有的权限
     * @param roleId 角色id
     * @return 权限列表
     */
    List<BarterPermissionInfo> queryPermissionByRoleId(Long roleId);

    /**
     * 查询所有权限id
     *
     * @param roleId 角色id
     * @return 角色拥有的权限id
     */
    List<Integer> queryPermissionIdByRoleId(Integer roleId);

    /**
     * 通过记录删除
     *
     * @param roleId rid
     * @param permissionId pid
     */
    int deleteByRecord(long roleId, Integer permissionId);
}

