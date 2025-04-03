package com.example.barter_good.common.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.barter_good.domain.entity.BarterGoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author chengke
 * @version 1.0
 * @className SetToListUtil
 * @description 将redis中存储商品信息的zset读入set，再转为List
 * @date 2021/11/24 17:18
 */
@Component
public class SetToListUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public List<BarterGoodsInfo> setToList(){
        Set goods = redisTemplate.opsForZSet().range("view_count_goods", 0, -1);

        assert goods != null;
        List<BarterGoodsInfo> barterGoodsInfoList = new ArrayList<>();

        goods.forEach(gds->{
            String jsonObject = JSON.toJSONString(gds);

            BarterGoodsInfo barterGoodsInfo = JSONObject.parseObject(jsonObject,BarterGoodsInfo.class);

            barterGoodsInfoList.add(barterGoodsInfo);
        });

        return barterGoodsInfoList;
    }

}