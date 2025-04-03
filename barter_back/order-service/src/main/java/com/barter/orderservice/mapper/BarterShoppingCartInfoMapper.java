package com.barter.orderservice.mapper;

import com.barter.orderservice.domain.entity.BarterShoppingCartInfo;
import com.barter.orderservice.domain.vo.ShoppingCartVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (BarterShoppingcartInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-28 23:35:55
 */
@Mapper
public interface BarterShoppingCartInfoMapper {

    /**
     * 通过ID查询单条数据
     * @param id 主键
     * @return 实例对象
     */
    BarterShoppingCartInfo queryById(Long id);

    /**
     * 通过UserId查询数据
     * @param id id
     * @return List<ShoppingCartVo>
     */
    List<ShoppingCartVo> queryByUserId(long id);

    /**
     * 通过用户id商品id，查询购物车id
     * @param userId userId
     * @param goodId goodId
     * @return long
     */
    long queryIdByUserIdGoodId(long userId,long goodId);

    /**
     * 查询指定行数据
     *
     * @param barterShoppingCartInfo 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<BarterShoppingCartInfo> queryAllByLimit(BarterShoppingCartInfo barterShoppingCartInfo, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param barterShoppingCartInfo 查询条件
     * @return 总行数
     */
    long count(BarterShoppingCartInfo barterShoppingCartInfo);

    /**
     * 统计用户购物车中商品数
     * @param userId userId
     * @param goodId goodId
     * @return long
     */
    long countByGoodId(long userId,long goodId);

    /**
     * 新增数据
     *
     * @param barterShoppingCartInfo 实例对象
     * @return 影响行数
     */
    int insert(BarterShoppingCartInfo barterShoppingCartInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterShoppingCartInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BarterShoppingCartInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterShoppingCartInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BarterShoppingCartInfo> entities);

    /**
     * 修改数据
     *
     * @param barterShoppingCartInfo 实例对象
     * @return 影响行数
     */
    int update(BarterShoppingCartInfo barterShoppingCartInfo);

    /**
     * 商品数量增加
     * @param userId userId
     * @param goodId goodId
     * @return int
     */
    int updateGoodNumI(long userId,long goodId);

    /**
     * 商品数量减少
     * @param goodId goodId
     * @param userId userId
     * @return int
     */
    int updateGoodNumD(long userId,long goodId);

    /**
     * 更新购物车中商品的数量
     * @param userId userId
     * @param goodId goodId
     * @param goodNum goodNum
     * @return int
     */
    int updateGoodNumById(long userId,long goodId,int goodNum);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 通过用户id和商品id删除购物车中的信息
     * @param userId userId
     * @param goodId goodId
     * @return int
     */
    int deleteByUserIdGoodId(long userId,long goodId);

    /**
     * 根据用户id和商品id查询购物车信息
     * @param userId userId
     * @param goodId goodId
     * @return BarterShoppingCartInfo
     */
    BarterShoppingCartInfo queryByUserIdGoodId(long userId,long goodId);
}

