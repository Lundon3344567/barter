package com.barter.orderservice.service.impl;

import com.github.pagehelper.PageInfo;
import com.barter.orderservice.common.util.PageUtil;
import com.barter.orderservice.common.util.SetToListUtil;
import com.barter.orderservice.domain.entity.BarterGoodsInfo;
import com.barter.orderservice.domain.entity.BarterShoppingCartInfo;
import com.barter.orderservice.domain.form.InsertShoppingCartForm;
import com.barter.orderservice.domain.form.PageForm;
import com.barter.orderservice.domain.vo.ShoppingCartVo;
import com.barter.orderservice.mapper.BarterShoppingCartInfoMapper;
import com.barter.orderservice.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author chengke
 * @version 1.0
 * @className ShoppingCartServiceImpl
 * @description 购物车业务实现类
 * @date 2021/11/29 21:26
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private SetToListUtil setToListUtil;

    @Autowired
    private BarterShoppingCartInfoMapper barterShoppingCartInfoMapper;




    /**
     * 插入购物车数据
     * @param insertShoppingCartForm insertShoppingCartForm
     * @return count
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public synchronized int adShoppingCart(InsertShoppingCartForm insertShoppingCartForm) {
        BarterShoppingCartInfo barterShoppingCartInfo = new BarterShoppingCartInfo();

        Date date = new Date();

        barterShoppingCartInfo.setGoodId(insertShoppingCartForm.getGoodId());
        barterShoppingCartInfo.setGoodNum(insertShoppingCartForm.getGoodNum());
        barterShoppingCartInfo.setUserId(insertShoppingCartForm.getUserId());
        barterShoppingCartInfo.setCreateTime(date);
        barterShoppingCartInfo.setLastEditTime(date);

        return barterShoppingCartInfoMapper.insert(barterShoppingCartInfo);
    }

    /**
     * 更新购物车数据
     * @param userId userId
     * @param goodId goodId
     * @param goodNum goodNum
     * @return count
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public synchronized int updataShoppingCart(Long userId,Long goodId,int goodNum) {

        int count;

        if(goodNum<1){
            count = -2;
            return count;
        }

        List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

        BarterGoodsInfo barterGoodsInfo = new BarterGoodsInfo();

        for (BarterGoodsInfo goodsInfo : barterGoodsInfoList) {
            if (goodsInfo.getGoodId().equals(goodId)) {
                barterGoodsInfo = goodsInfo;
            }
        }

        int goodStoreNum = barterGoodsInfo.getGoodNum();

        if(goodNum>goodStoreNum){
            count = -1;
            return count;
        }

        count = barterShoppingCartInfoMapper.updateGoodNumById(userId,goodId,goodNum);

        return count;
    }

    /**
     * 通过用户id和商品id删除购物车中的信息
     * @param userId userId
     * @param goodId goodId
     * @return count
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public synchronized int deleteShoppingCart(Long userId,Long goodId) {

        return barterShoppingCartInfoMapper.deleteByUserIdGoodId(userId,goodId);
    }

    /**
     * 根据用户id分页查询购物车信息
     * @param pageForm pageform
     * @param userId userId
     * @return PageInfo<ShoppingCartVo>u8
     */
    @Override
    public PageInfo<ShoppingCartVo> queryShoppingCartByPage(PageForm pageForm, Long userId) {

        List<ShoppingCartVo> shoppingCartVos = barterShoppingCartInfoMapper.queryByUserId(userId);

        return PageUtil.pageList(pageForm,shoppingCartVos);
    }

    /**
     * 根据用户id和商品id查询用户是否访问将该商品加入购物车
     * @param userId userId
     * @param goodId goodId
     * @return int
     */
    @Override
    public int queryShoppingGoodsById(Long userId, Long goodId) {

        BarterShoppingCartInfo barterShoppingCartInfo = barterShoppingCartInfoMapper.queryByUserIdGoodId(userId,goodId);

        if(barterShoppingCartInfo != null){
            return 1;
        }

        return 0;
    }


}