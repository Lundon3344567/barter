package com.example.barter_good.mapper;


import com.example.barter_good.domain.entity.BarterGoodsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (BarterGoodsInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-20 14:44:45
 */
@Mapper
public interface BarterGoodsInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param goodId 主键
     * @return 实例对象
     */
    BarterGoodsInfo queryById(Long goodId);

    int queryByGoodId(Long goodId);

    /**
     * 查询指定行数据
     *
     * @param barterGoodsInfo 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<BarterGoodsInfo> queryAllByLimit(BarterGoodsInfo barterGoodsInfo, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param barterGoodsInfo 查询条件
     * @return 总行数
     */
    long count(BarterGoodsInfo barterGoodsInfo);

    /**
     * 新增数据
     *
     * @param barterGoodsInfo 实例对象
     * @return 影响行数
     */
    int insert(BarterGoodsInfo barterGoodsInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterGoodsInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BarterGoodsInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BarterGoodsInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BarterGoodsInfo> entities);

    /**
     * 修改数据
     *
     * @param barterGoodsInfo 实例对象
     * @return 影响行数
     */
    int update(BarterGoodsInfo barterGoodsInfo);

    /**
     * 通过主键删除数据
     *
     * @param goodId 主键
     * @return 影响行数
     */
    int deleteById(Long goodId);

    /**
     * 获取所有商品
     * @return List<BarterGoodsInfo>
     */
    List<BarterGoodsInfo> queryAll();

    List<BarterGoodsInfo> queryByData();

    /**
     * 根据商品名称查询商品(模糊查询)
     * @param goodName goodName
     * @return List<BarterGoodsInfo>
     */
    List<BarterGoodsInfo> queryByName(String goodName);

    /**
     * 按创建时间排序商品
     * @return List<BarterGoodsInfo>
     */
    List<BarterGoodsInfo> queryByTime();

    /**
     * 根据分类筛选商品
     * @param id id
     * @return List<BarterGoodsInfo>
     */
    List<BarterGoodsInfo> queryByClassify(Long id);

    /**
     * 根据收藏数排序商品
     * @return List<BarterGoodsInfo>
     */
    List<BarterGoodsInfo> queryByCollection();

    /**
     * 根据价格排序商品
     * @return List<BarterGoodsInfo>
     */
    List<BarterGoodsInfo> queryByPrice();

    /**
     * 根据组合条件筛选商品
     * @return List<BarterGoodsInfo>
     */
    List<BarterGoodsInfo> queryByAll(Long id,String name,Integer collectionType,Integer priceType,Integer timeType,Integer viewCountType);

    /**
     * 根据父id与商品分类表联表查询
     * @param parentId 父类id
     * @return 父id下全部商品信息
     */
    List<BarterGoodsInfo> queryByParentId(Long parentId);

    /**
     * 根据浏览量排序商品
     * @return List<BarterGoodsInfo>
     */
    List<BarterGoodsInfo> queryByViewCount();

    /**
     * 根据用户id查询商品
     * @param userId userId
     * @param type type
     * @return List<BarterGoodsInfo>
     */
    List<BarterGoodsInfo> queryByUserId(long userId,Integer type);

    List<BarterGoodsInfo> queryByUserIdAll(long userId);

    /**
     * 根据商品id减少相应的num
     * @param goodId goodId
     * @param num num
     * @return List<BarterGoodsInfo>
     */
    int updateSubtract(long goodId,int num);

    /**
     * 根据商品Id增加相应的num
     * @param goodId goodId
     * @param num num
     * @return List<BarterGoodsInfo>
     */
    int updateAddNum(long goodId,int num);

    /**
     * 根据商品id获取分类id
     * @param goodId goodId
     * @return long
     */
    long selectClassifiidByGoodId(long goodId);

    /**
     * 订单查库存
     * @param goodsId 商品id
     * @return 商品库存数量
     */
    int queryGoodsNum(long goodsId);

//    /**
//     * 商品加库存
//     * @param goodsId 商品id
//     * @param num 数量
//     * @return 影响行数
//     */
//    int increaseGoodsNum(long goodsId,int num);
//
//    /**
//     * 商品减库存
//     * @param goodsId 商品id
//     * @param num 数量
//     * @return 影响行数
//     */
//    int decreaseGoodsNum(long goodsId,int num);
}

