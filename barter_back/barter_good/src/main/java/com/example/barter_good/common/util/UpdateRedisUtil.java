package com.example.barter_good.common.util;

import com.example.barter_good.domain.entity.BarterGoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chengke
 * @version 1.0
 * @className UpdateRedisUtil
 * @description 将查出来的数据更新到redis中
 * @date 2021/12/10 19:37
 */
@Component
public class UpdateRedisUtil {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 将查出来的商品信息列表更新到redis中
     * @param barterGoodsInfoList 查出来的商品信息列表
     */
    public void updateRedisList(List<BarterGoodsInfo> barterGoodsInfoList){
        barterGoodsInfoList.forEach(barterGoodsInfo -> {
            //将浏览量写入redis,(id,view_count)
            redisUtil.zSet("view_count_id",barterGoodsInfo.getGoodId(),barterGoodsInfo.getViewCount().doubleValue());
            redisUtil.zSet("view_count_id_new",barterGoodsInfo.getGoodId(),barterGoodsInfo.getViewCount().doubleValue());
            redisUtil.zSet("view_count_goods", barterGoodsInfo,barterGoodsInfo.getViewCount().doubleValue());
        });
    }

    /**
     * 将查出来的单个商品信息更新到redis中
     * @param barterGoodsInfo 查出来的单个商品对象
     */
    public void updateRedisGood(BarterGoodsInfo barterGoodsInfo){
        redisUtil.zSet("view_count_goods", barterGoodsInfo,barterGoodsInfo.getViewCount().doubleValue());
    }

}