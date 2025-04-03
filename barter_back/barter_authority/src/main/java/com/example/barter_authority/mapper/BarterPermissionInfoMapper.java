package com.example.barter_authority.mapper;

import com.example.barter_authority.domain.entity.BarterPermissionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (BarterPermissionInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-12 13:42:03
 */
@Mapper
public interface BarterPermissionInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    BarterPermissionInfo queryById(Long id);

    List<BarterPermissionInfo> queryByIds(List<Long> ids);

    /**
     * 查询指定行数据
     *
     * @param barterPermissionInfo 查询条件
     * @param pageable             分页对象
     * @return 对象列表
     */
    List<BarterPermissionInfo> queryAllByLimit(BarterPermissionInfo barterPermissionInfo, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param barterPermissionInfo 查询条件
     * @return 总行数
     */
    long count(BarterPermissionInfo barterPermissionInfo);

    /**
     * 新增数据
     *
     * @param barterPermissionInfo 实例对象
     * @return 影响行数
     */
    int insert(BarterPermissionInfo barterPermissionInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterPermissionInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BarterPermissionInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterPermissionInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BarterPermissionInfo> entities);

    /**
     * 修改数据
     *
     * @param barterPermissionInfo 实例对象
     * @return 影响行数
     */
    int update(BarterPermissionInfo barterPermissionInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);


    /**
     * 获取菜单操作编码
     *
     * @param userId
     * @return
     */
    List<Long> getNavMenuIds(Long userId);

    /**
     * 查询所有的权限
     *
     * @return
     */
    List<BarterPermissionInfo> queryAll();

}

