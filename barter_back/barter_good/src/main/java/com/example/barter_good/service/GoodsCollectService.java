package com.example.barter_good.service;



import com.example.barter_good.domain.entity.BarterGoodsCollect;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @ClassName GoodsCollectService
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-13 15:55
 * @Version 1.0
 **/
@Service
public interface GoodsCollectService {

    /**
     * 商品收藏
     * @param barterGoodsCollect
     * @return
     */
    int goodsCollectAdd(BarterGoodsCollect barterGoodsCollect);

    /**
     * 取消商品收藏
     * @param goodsId,userId
     * @return
     */
    int goodsCollectDelete(long goodsId,long userId);

    /**
     * 根据商品id统计商品收藏数
     * @param goodsId
     * @return
     */
    long goodsByGoodid(long goodsId);

    /**
     * 根据用户id统计已收藏的商品数
     * @param userId
     * @return
     */
    long goodsByUserid(long userId);

    /**
     * 根据用户id列出已收藏的商品
     * @param usrId
     * @return
     */
    List<BarterGoodsCollect> selectByUserid(long usrId);

    /**
     * 查看商品是否被用户收藏
     * @param goodsId
     * @param userId
     * @return
     */
    int collectByGoodsidUserid(long goodsId,long userId);
}
