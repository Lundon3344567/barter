package com.example.barter_good.common.util;

import com.example.barter_good.domain.entity.BarterGoodsInfo;
import org.springframework.stereotype.Component;

/**
 * @author chengke
 * @version 1.0
 * @className GoodIsNull
 * @description 判断商品各属性是否为空,若不为空则更新到目标类
 * @date 2021/11/30 19:59
 */
@Component
public class GoodIsNullUtil {

    public void updateGoodInList(BarterGoodsInfo barterGoodsInfoFrom, BarterGoodsInfo barterGoodsInfoTo){
        if (barterGoodsInfoFrom.getOwnerId() != null){
            barterGoodsInfoTo.setOwnerId(barterGoodsInfoFrom.getOwnerId());
        }
        if(barterGoodsInfoFrom.getGoodName() != null){
            barterGoodsInfoTo.setGoodName(barterGoodsInfoFrom.getGoodName());
        }
        if(barterGoodsInfoFrom.getGoodPrice() != null){
            barterGoodsInfoTo.setGoodPrice(barterGoodsInfoFrom.getGoodPrice());
        }
        if(barterGoodsInfoFrom.getGoodNum() != null){
            barterGoodsInfoTo.setGoodNum(barterGoodsInfoFrom.getGoodNum());
        }
        if(barterGoodsInfoFrom.getGoodFlag() != null){
            barterGoodsInfoTo.setGoodFlag(barterGoodsInfoFrom.getGoodFlag());
        }
        if(barterGoodsInfoFrom.getImgUrl() != null){
            barterGoodsInfoTo.setImgUrl(barterGoodsInfoFrom.getImgUrl());
        }
        if(barterGoodsInfoFrom.getGoodDescribe() != null){
            barterGoodsInfoTo.setGoodDescribe(barterGoodsInfoFrom.getGoodDescribe());
        }
        if(barterGoodsInfoFrom.getCollectionNum() != null){
            barterGoodsInfoTo.setCollectionNum(barterGoodsInfoFrom.getCollectionNum());
        }
        if(barterGoodsInfoFrom.getClassifiedNum() != null){
            barterGoodsInfoTo.setClassifiedNum(barterGoodsInfoFrom.getClassifiedNum());
        }
        if(barterGoodsInfoFrom.getCreateTime() != null){
            barterGoodsInfoTo.setCreateTime(barterGoodsInfoFrom.getCreateTime());
        }
        if(barterGoodsInfoFrom.getViewCount() != null){
            barterGoodsInfoTo.setViewCount(barterGoodsInfoFrom.getViewCount());
        }
    }

}