package com.example.barter_authority.mapper;

import com.example.barter_authority.domain.entity.BarterRoleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (BarterRoleInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-13 00:19:15
 */
@Mapper
public interface BarterRoleInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    BarterRoleInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param barterRoleInfo 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<BarterRoleInfo> queryAllByLimit(BarterRoleInfo barterRoleInfo, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param barterRoleInfo 查询条件
     * @return 总行数
     */
    long count(BarterRoleInfo barterRoleInfo);

    /**
     * 新增数据
     *
     * @param barterRoleInfo 实例对象
     * @return 影响行数
     */
    int insert(BarterRoleInfo barterRoleInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterRoleInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BarterRoleInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterRoleInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BarterRoleInfo> entities);

    /**
     * 修改数据
     *
     * @param barterRoleInfo 实例对象
     * @return 影响行数
     */
    int update(BarterRoleInfo barterRoleInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 获取所有的角色
     *
     * @return 所有角色
     */
    List<BarterRoleInfo> queryAll();
}

