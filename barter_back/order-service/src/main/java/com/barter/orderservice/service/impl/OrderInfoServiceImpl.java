package com.barter.orderservice.service.impl;

import com.barter.orderservice.service.goodsClient;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
//import com.barter.orderservice.common.util.RedisUtil;
//import com.barter.orderservice.common.util.SetToListUtil;
//import com.barter.orderservice.domain.entity.BarterChatLog;
import com.barter.orderservice.domain.entity.BarterGoodsInfo;
import com.barter.orderservice.domain.entity.BarterOrderInfo;
import com.barter.orderservice.domain.form.OrderFrom;
import com.barter.orderservice.domain.form.PageForm;
import com.barter.orderservice.mapper.*;
//import com.barter.orderservice.service.GoodsInfoService;
import com.barter.orderservice.service.OrderInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName OrderInfoServiceImpl
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-29 17:11
 * @Version 1.0
 **/
@Service
public class OrderInfoServiceImpl implements OrderInfoService {


    @Autowired
    private BarterOrderInfoMapper barterOrderInfoMapper;

    @Autowired
    private BarterGoodsInfoMapper barterGoodsInfoMapper;

    @Autowired
    private BarterShoppingCartInfoMapper barterShoppingCartInfoMapper;


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private goodsClient goodsclient;



    //保证创建订单是在同一个事务当中
    @CacheEvict(value = "count",allEntries = true)
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addOrder(List<OrderFrom> orderFromList) {
        int result = 1;

        BarterOrderInfo barterOrderInfo = new BarterOrderInfo();

        //初步校验数据，用户与商品存在与否，商品库存(缓存)
        boolean f = true;
        for (OrderFrom orderFrom : orderFromList) {
            BeanUtils.copyProperties(orderFrom, barterOrderInfo, BarterOrderInfo.class);
            if(barterGoodsInfoMapper.queryByGoodId(barterOrderInfo.getGoodId()) == 0 ){
                f = false;
                break;
            }
            //查还剩多少
            if(goodsclient.queryGoodsNum(barterOrderInfo.getGoodId()) == false){
                f = false;
                break;
            }
//            if(barterOrderInfo.getGoodNum() > goodsInfoService.getListById(barterOrderInfo.getGoodId()).getGoodNum()){
//                f = false;
//                break;
//            }
        }
        if (f) {
            //保存订单  减少库存  删除购物车
            for (OrderFrom orderFrom : orderFromList) {
                BeanUtils.copyProperties(orderFrom, barterOrderInfo, BarterOrderInfo.class);
                barterOrderInfo.setSellerId(barterGoodsInfoMapper.queryById(barterOrderInfo.getGoodId()).getOwnerId());
                barterOrderInfo.setPayMoney(barterGoodsInfoMapper.queryById(barterOrderInfo.getGoodId()).getGoodPrice()*orderFrom.getGoodNum());
                barterOrderInfo.setOrderMoney(barterGoodsInfoMapper.queryById(barterOrderInfo.getGoodId()).getGoodPrice()*orderFrom.getGoodNum());
                barterOrderInfo.setCreateTime(new Date());
                barterOrderInfo.setLastEditTime(new Date(System.currentTimeMillis()+900000));
                barterOrderInfo.setOrderFlag(0);

                //保存订单
                barterOrderInfoMapper.insert(barterOrderInfo);
                //放入缓存
                redisTemplate.opsForValue().set("orderInfo:" + barterOrderInfo.getId(), barterOrderInfo.getId() + "", 15, TimeUnit.MINUTES);

                //减少库存
                if(goodsclient.increaseGoodsNum(barterOrderInfo.getGoodId(), barterOrderInfo.getGoodNum(),0) == false){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return 0;
                }
//                if(barterGoodsInfoMapper.updateSubtract(barterOrderInfo.getGoodId(), barterOrderInfo.getGoodNum()) == 0){
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                    return 0;
//                }

                //删除购物车记录
                if(barterShoppingCartInfoMapper.queryByUserIdGoodId(barterOrderInfo.getBuyerId(), barterOrderInfo.getGoodId()) != null){
                    barterShoppingCartInfoMapper.deleteById(barterShoppingCartInfoMapper.queryIdByUserIdGoodId(barterOrderInfo.getBuyerId(), barterOrderInfo.getGoodId()));
                }
            }
        } else {
            result = 0;
        }
        return result;
    }

    @Override
    public int cancelOrder(long id) {
        int rows = barterOrderInfoMapper.updateFlag(id);
        if(rows <= 0){
            return 0;
        }
        return 1;
    }

    @Override
    public int paymentOrder(long id) {
        BarterOrderInfo barterOrderInfo = new BarterOrderInfo();
        barterOrderInfo.setId(id);
        barterOrderInfo.setOrderFlag(1);
        barterOrderInfo.setPayTime(new Date());
        barterOrderInfoMapper.update(barterOrderInfo);
        return 1;
    }

    @Override
    public PageInfo<BarterOrderInfo> queryOrderByUserId(PageForm pageForm, long userId) {
        PageHelper.startPage(pageForm.getRequestPage(),pageForm.getPageSize());
        List<BarterOrderInfo> barterOrderInfoList = barterOrderInfoMapper.queryByUserId(userId);
        return new PageInfo<BarterOrderInfo>(barterOrderInfoList);
    }

    @Override
    public PageInfo<BarterOrderInfo> queryOrderAll(PageForm pageForm) {
        PageHelper.startPage(pageForm.getRequestPage(),pageForm.getPageSize());
        List<BarterOrderInfo> barterOrderInfoList = barterOrderInfoMapper.queryAll();
        return new PageInfo<>(barterOrderInfoList);
    }

    @Override
    public long expirationTimeByKey(long id) {
        return redisTemplate.getExpire("orderInfo:" + id);
    }


    @Override
    public PageInfo<BarterOrderInfo> queryOrderFlagByUserId(PageForm pageForm, long userId, int flag) {
        PageHelper.startPage(pageForm.getRequestPage(),pageForm.getPageSize());
        List<BarterOrderInfo> barterOrderInfoList = barterOrderInfoMapper.queryOrderFlagByUserId(userId,flag);
        return new PageInfo<>(barterOrderInfoList);
    }



}
