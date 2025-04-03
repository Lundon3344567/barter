package com.barter.orderservice.mapper;

import com.barter.orderservice.domain.entity.BarterOrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (BarterOrderInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-28 23:35:27
 */
@Mapper
public interface BarterOrderInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    BarterOrderInfo queryById(Long id);

    /**
     * 通过用户Id查询用户订单
     * @param userId
     * @return
     */
    List<BarterOrderInfo> queryByUserId(long userId);

    /**
     * 查询全部订单
     * @return
     */
    List<BarterOrderInfo> queryAll();

    /**
     * 用户根据状态查看订单
     * @param userId
     * @param flag
     * @return
     */
    List<BarterOrderInfo> queryOrderFlagByUserId(long userId, int flag);

    /**
     * 查询指定行数据
     *
     * @param barterOrderInfo 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<BarterOrderInfo> queryAllByLimit(BarterOrderInfo barterOrderInfo, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param barterOrderInfo 查询条件
     * @return 总行数
     */
    long count(BarterOrderInfo barterOrderInfo);

    /**
     * 新增数据
     *
     * @param barterOrderInfo 实例对象
     * @return 影响行数
     */
    int insert(BarterOrderInfo barterOrderInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterOrderInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BarterOrderInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterOrderInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BarterOrderInfo> entities);

    /**
     * 修改数据
     *
     * @param barterOrderInfo 实例对象
     * @return 影响行数
     */
    int update(BarterOrderInfo barterOrderInfo);

    /**
     * 修改订单状态为-1
     * @param id
     * @return
     */
    int updateFlag(long id);

    int updatePaymentFlagById(long id);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

