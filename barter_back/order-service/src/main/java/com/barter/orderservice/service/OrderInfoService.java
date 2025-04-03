package com.barter.orderservice.service;

import com.github.pagehelper.PageInfo;
//import com.barter.orderservice.domain.entity.BarterChatLog;
import com.barter.orderservice.domain.entity.BarterOrderInfo;
import com.barter.orderservice.domain.form.OrderFrom;
import com.barter.orderservice.domain.form.PageForm;

import java.util.List;

/**
 * @ClassName OrderInfoService
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-29 17:10
 * @Version 1.0
 **/
public interface OrderInfoService {

    /**
     * 增加订单
     * @param orderFromList
     * @return
     */
    int addOrder(List<OrderFrom> orderFromList);

    /**
     * 未支付成功时手动取消订单
     * @param id
     * @return
     */
    int cancelOrder(long id);

    /**
     * 模拟支付订单
     * @param id
     * @return
     */
    int paymentOrder(long id);

    /**
     * 通过用户id分页查询用户订单
     * @param pageForm
     * @param userId
     * @return
     */
    PageInfo<BarterOrderInfo> queryOrderByUserId(PageForm pageForm,long userId);

    /**
     * 分页查询所有订单
     * @param pageForm
     * @return
     */
    PageInfo<BarterOrderInfo> queryOrderAll(PageForm pageForm);

    /**
     * 获取订单过期时间
     * @param id
     * @return
     */
    long expirationTimeByKey(long id);

    /**
     * 用户根据状态查看订单
     * @param pageForm
     * @param userId
     * @param flag
     * @return
     */
    PageInfo<BarterOrderInfo> queryOrderFlagByUserId(PageForm pageForm,long userId,int flag);


    /**
     * 商家查看推送消息
     * @param pageForm
     * @param userId
     * @return
     */
//    PageInfo<BarterChatLog> querySystemByUserId(PageForm pageForm,long userId);

    /**
     * 将未读消息置为已读
     * @param chatId
     * @return
     */
//    int updateStatusByChatId(long chatId);

    /**
     * 删除单条消息
     * @param chatId
     * @return
     */
//    int deleteByChatId(long chatId);

    /**
     * 一键删除已读消息
     * @param userId
     * @return
     */
//    int deleteStatusByChatId(long userId);



}
