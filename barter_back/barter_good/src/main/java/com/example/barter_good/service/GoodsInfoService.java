package com.example.barter_good.service;


import com.example.barter_good.domain.entity.BarterGoodsInfo;
import com.example.barter_good.domain.form.InsertGoodsForm;
import com.example.barter_good.domain.form.PageForm;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Chengke
 * @create 2021/11/9
 */
@Service
public interface GoodsInfoService {

    /**
     * 分页查询所有商品
     * @param pageForm pageForm
     * @return PageInfo<BarterGoodsInfo>
     */
    PageInfo<BarterGoodsInfo> queryGoodsByPage(PageForm pageForm);

    /**
     * 根据id查询商品
     * @param id id
     * @return BarterGoodsInfo
     */
    BarterGoodsInfo getListById(Long id);

    /**
     * 根据id查询商品
     * @param id id
     * @return BarterGoodsInfo
     */
    BarterGoodsInfo queryGoodById(Long id);

    /**
     * 插入商品
     * @param insertGoodsForm insertGoodsForm
     * @return int
     */
    int adGoods(InsertGoodsForm insertGoodsForm);

    /**
     * 更新商品信息
     * @param barterGoodsInfo barterGoodsInfo
     * @return int
     */
    int updateGoods(BarterGoodsInfo barterGoodsInfo);

    /**
     * 删除商品
     * @param id id
     * @return int
     */
    int deleteGoods(Long id);

    /**
     * 获取所有商品用于更新访问量
     * @return List<BarterGoodsInfo>
     */
    List<BarterGoodsInfo> queryAll();

    /**
     * 获取推荐商品
     * @param pageForm pageForm
     * @return PageInfo<BarterGoodsInfo>
     */
    PageInfo<BarterGoodsInfo> recommendGoods(PageForm pageForm);

    /**
     * 普通用户模糊查询商品
     * @param pageForm pageForm
     * @param goodName goodName
     * @return PageInfo<BarterGoodsInfo>
     */
    PageInfo<BarterGoodsInfo> queryByName(PageForm pageForm,String goodName);

    /**
     * 管理员模糊查询商品
     * @param pageForm pageForm
     * @param goodName goodName
     * @return PageInfo<BarterGoodsInfo>
     */
    PageInfo<BarterGoodsInfo> adQueryByName(PageForm pageForm,String goodName);

    /**
     * 根据创建时间排序商品
     * @param pageForm pageForm
     * @return PageInfo<BarterGoodsInfo>
     */
    PageInfo<BarterGoodsInfo> queryGoodsOrderByTime(PageForm pageForm);

    /**
     * 根据分类筛选商品
     * @param pageForm pageForm
     * @param id id
     * @return PageInfo<BarterGoodsInfo>
     */
    PageInfo<BarterGoodsInfo> queryByClassification(PageForm pageForm,Long id);

    /**
     * 根据收藏数排序商品
     * @param pageForm pageForm
     * @return PageInfo<BarterGoodsInfo>
     */
    PageInfo<BarterGoodsInfo> queryByCollection(PageForm pageForm);

    /**
     * 根据价格排序商品
     * @param pageForm pageForm
     * @return PageInfo<BarterGoodsInfo>
     */
    PageInfo<BarterGoodsInfo> queryByPrice(PageForm pageForm);

    /**
     * 根据组合条件筛选商品
     * @param pageForm pageForm
     * @param id id
     * @param name name
     * @param collectionType collectionType
     * @param priceType priceType
     * @param timeType timeType
     * @param viewCountType viewCountType
     * @return PageInfo<BarterGoodsInfo>
     */
    PageInfo<BarterGoodsInfo> queryByAll(PageForm pageForm,Long id,String name,Integer collectionType,Integer priceType,Integer timeType,Integer viewCountType);

    /**
     * 订单服务查商品库存
     * @param goodsId 商品id
     * @return 是否有库存
     */
    Boolean queryGoodsNum(Long goodsId);

    /**
     * 订单加库存
     * @param goodsId 商品id
     * @param num 数量
     */
    Boolean increaseGoodsNum(Long goodsId,Integer num);

    /**
     * 订单减库存
     * @param goodsId 商品id
     * @param num 数量
     */
    Boolean decreaseGoodsNum(Long goodsId,Integer num);

    /**
     * 分页查询用户拥有的商品信息
     * @param pageForm
     * @param userId
     * @param type
     * @return
     */
    PageInfo<BarterGoodsInfo> queryGoodsByUserId(PageForm pageForm, long userId, int type);
}
