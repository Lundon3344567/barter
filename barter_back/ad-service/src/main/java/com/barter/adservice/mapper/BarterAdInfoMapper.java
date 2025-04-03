package com.barter.adservice.mapper;

import com.barter.adservice.domain.entity.BarterAdInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (BarterAdInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-09 16:05:44
 */
@Mapper
public interface BarterAdInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param adId 主键
     * @return 实例对象
     */
    BarterAdInfo queryById(Long adId);

    /**
     * 查询指定行数据
     *
     * @param barterAdInfo 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    //List<BarterAdInfo> queryAllByLimit(BarterAdInfo barterAdInfo, @Param("pageable") Pageable pageable);

//    List<BarterAdInfo> queryAllByLimit(BarterAdInfo barterAdInfo,Pageable pageable);

    List<BarterAdInfo> queryAllByLimit(BarterAdInfo barterAdInfo);

    List<BarterAdInfo> queryAll();

    List<BarterAdInfo> adSelect();

    List<BarterAdInfo> adSelectOrderBy();
    /**
     * 统计总行数
     *
     * @param barterAdInfo 查询条件
     * @return 总行数
     */
    long count(BarterAdInfo barterAdInfo);

    /**
     * 新增数据
     * @param barterAdInfo 实例对象
     * @return 影响行数
     */
    int insert(BarterAdInfo barterAdInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterAdInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BarterAdInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterAdInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BarterAdInfo> entities);

    /**
     * 修改数据
     *
     * @param barterAdInfo 实例对象
     * @return 影响行数
     */
    int update(BarterAdInfo barterAdInfo);

    /**
     * 通过主键删除数据
     *
     * @param adId 主键
     * @return 影响行数
     */
    int deleteById(Long adId);

}

