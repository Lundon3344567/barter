package com.barter.orderservice.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MyMQConfig
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-12-30 9:24
 * @Version 1.0
 **/
@Configuration
public class MyMQConfig {

    /**
     * 使用JSON序列化机制，进行消息转换
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 队列
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "order-event-exchange");
        arguments.put("x-dead-letter-routing-key", "order.release.order");
        //消息过期时间
        arguments.put("x-message-ttl", 60000);
        Queue orderDelayQueue = new Queue("order.delay.queue", true, false, false, arguments);
        return orderDelayQueue;
    }

    /**
     * 队列
     * @return
     */
    @Bean
    public Queue orderReleaseQueue() {
        return new Queue("order.release.order.queue", true, false, false);
    }

//    @Bean
//    //监听秒杀业务队列
//    public Queue orderSeckillOrderQueue(){
//        return new Queue("order.seckill.order.queue",true,false,false);
//    }

    /**
     * 交换机
     * @return
     */
    @Bean
    public Exchange orderEventExchange() {
        return new TopicExchange("order-event-exchange", true, false);
    }

    /**
     * 绑定
     * @return
     */
    @Bean
    public Binding orderCreateBingding() {
        //目的地  目的地类型  绑定的交换机
        return new Binding("order.delay.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.create.order", null);
    }

    /**
     * 绑定
     * @return
     */
    @Bean
    public Binding orderReleaseBingding() {
        return new Binding("order.release.order.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.release.order", null);
    }

//    @Bean
//    public Binding orderReleaseOtherBingding() {
//        return new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.release.other.#", null);
//    }
//
//    @Bean
//    //秒杀业务绑定关系
//    public Binding orderSeckillOrderQueueBinding(){
//        return new Binding("order.seckill.order.queue",Binding.DestinationType.QUEUE,"order-event-exchange","order.seckill.order",null);
//    }
}
