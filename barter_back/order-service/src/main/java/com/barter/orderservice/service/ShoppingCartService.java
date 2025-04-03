package com.barter.orderservice.service;

import com.github.pagehelper.PageInfo;
import com.barter.orderservice.domain.form.InsertShoppingCartForm;
import com.barter.orderservice.domain.form.PageForm;
import com.barter.orderservice.domain.vo.ShoppingCartVo;
import org.springframework.stereotype.Service;

/**
 * @author chengke
 * @version 1.0
 * @description 购物车业务层
 * @date 2021/11/29 21:17
 */
@Service
public interface ShoppingCartService {

    /**
     * 插入购物车数据
     * @param insertShoppingCartForm insertShoppingCartForm
     * @return int
     */
    int adShoppingCart(InsertShoppingCartForm insertShoppingCartForm);

    /**
     * 更新购物车数据
     * @param userId userId
     * @param goodId goodId
     * @param goodNum goodNum
     * @return int
     */
    int updataShoppingCart(Long userId,Long goodId,int goodNum);

    /**
     * 通过用户id和商品id删除购物车中的信息
     * @param userId userId
     * @param goodId goodId
     * @return int
     */
    int deleteShoppingCart(Long userId,Long goodId);

    /**
     * 根据用户id分页查询购物车信息
     * @param pageForm pageform
     * @param userId userId
     * @return PageInfo<ShoppingCartVo>
     */
    PageInfo<ShoppingCartVo> queryShoppingCartByPage(PageForm pageForm, Long userId);

    /**
     * 根据用户id和商品id查询用户是否访问将该商品加入购物车
     * @param userId userId
     * @param goodId goodId
     * @return int
     */
    int queryShoppingGoodsById(Long userId,Long goodId);
}
