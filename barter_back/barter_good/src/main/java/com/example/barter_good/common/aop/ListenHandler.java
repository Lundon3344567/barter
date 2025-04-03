//package com.example.barter_good.common.aop;
//
//
//import com.example.barter_good.common.util.RedisUtil;
//import com.example.barter_good.domain.entity.BarterGoodsInfo;
//import com.example.barter_good.interceptor.RedisBloomFilter;
//import com.example.barter_good.service.GoodsInfoService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Set;
//
///**
// * @author chengke
// * @version 1.0
// * @className ListenHandler
// * @description 启动时进行操作的监听类
// * @date 2021/11/12 15:28
// */
//
//
///*
//HIGHEST_PRECEDENCE代表这个过滤器在众多过滤器中级别最高，也就是过滤的时候最先执行
// */
//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class ListenHandler {
//
//    @Autowired
//    private GoodsInfoService goodsInfoService;
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @Autowired
//    private RedisBloomFilter redisBloomFilter;
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    /*
//    程序加载后执行，将数据库中的数据写入redis
//     */
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void init() {
//        this.logger.info("数据库初始化开始");
//
//        /*keys方法 进行模糊匹配*/
//        for (int i = 1; i <= 10; i++) {
//            Set keys = redisTemplate.keys("S" + "*");
//            /*执行删除*/
//            redisTemplate.delete(keys);
//        }
//
//        redisUtil.del("view_count_id");
//        redisUtil.del("view_count_id_new");
//        redisUtil.del("view_count_goods");
//
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        /*
//        将数据库中的数据写入redis,并初始化布隆过滤器（放入商品id）
//         */
//        List<BarterGoodsInfo> goodsInfoList = goodsInfoService.queryAll();
//        goodsInfoList.forEach(barterGoodsInfo -> {
//            //初始化布隆过滤器（放入商品id）,加上S前缀便于删除
//            String str = "S"+barterGoodsInfo.getGoodId();
//            redisBloomFilter.put(str);
//            //放入商品信息
//            redisUtil.zSet("view_count_id",barterGoodsInfo.getGoodId(),barterGoodsInfo.getViewCount().doubleValue());
//            redisUtil.zSet("view_count_id_new",barterGoodsInfo.getGoodId(),barterGoodsInfo.getViewCount().doubleValue());
//            redisUtil.zSet("view_count_goods", barterGoodsInfo,barterGoodsInfo.getViewCount().doubleValue());
//        });
//
//        this.logger.info("已写入redis");
//    }
//}