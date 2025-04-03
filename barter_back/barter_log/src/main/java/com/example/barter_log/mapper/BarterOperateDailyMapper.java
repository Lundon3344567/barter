package com.example.barter_log.mapper;

import com.example.barter_log.domain.entity.BarterOperateDaily;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (BarterOperateDaily)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-22 16:21:41
 */
@Mapper
public interface BarterOperateDailyMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param operateId 主键
     * @return 实例对象
     */
    BarterOperateDaily queryById(String operateId);

    /**
     * 查询指定行数据
     *
     * @param barterOperateDaily 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<BarterOperateDaily> queryAllByLimit(BarterOperateDaily barterOperateDaily, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param barterOperateDaily 查询条件
     * @return 总行数
     */
    long count(BarterOperateDaily barterOperateDaily);

    /**
     * 新增数据
     *
     * @param barterOperateDaily 实例对象
     * @return 影响行数
     */
    int insert(BarterOperateDaily barterOperateDaily);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterOperateDaily> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BarterOperateDaily> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterOperateDaily> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BarterOperateDaily> entities);

    /**
     * 修改数据
     *
     * @param barterOperateDaily 实例对象
     * @return 影响行数
     */
    int update(BarterOperateDaily barterOperateDaily);

    /**
     * 通过主键删除数据
     *
     * @param operateId 主键
     * @return 影响行数
     */
    int deleteById(String operateId);

    /**
     * 查询所有
     * @return 所有日志
     */
    List<BarterOperateDaily> queryAll();

    /**
     * 删除所有操作日志
     */
    void deleteAllOperate();

    /**
     * 删除所有异常日志
     */
    void deleteAllException();

    /**
     * 查询指定日志
     * @param type 类型
     * @return 日志信息
     */
    List<BarterOperateDaily> queryByCondition(String type);
}

