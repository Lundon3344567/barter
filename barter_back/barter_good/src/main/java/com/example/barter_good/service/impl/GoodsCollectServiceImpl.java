package com.example.barter_good.service.impl;


import com.example.barter_good.domain.entity.BarterGoodsCollect;
import com.example.barter_good.mapper.BarterGoodsCollectMapper;
import com.example.barter_good.mapper.BarterGoodsInfoMapper;
import com.example.barter_good.service.GoodsCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName GoodsCollectServiceImpl
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-13 15:55
 * @Version 1.0
 **/
@Service
public class GoodsCollectServiceImpl implements GoodsCollectService {

    @Autowired
    private BarterGoodsCollectMapper barterGoodsCollectMapper;

    @Autowired
    private BarterGoodsInfoMapper barterGoodsInfoMapper;

    /**
     * 添加商品收藏
     * @param barterGoodsCollect
     * @return
     */
    @Override
    public int goodsCollectAdd(BarterGoodsCollect barterGoodsCollect) {
        barterGoodsCollect.setCreatedTime(new Date());
        barterGoodsCollect.setState(1);
        barterGoodsCollectMapper.insert(barterGoodsCollect);
//        barterGoodsInfoMapper.addNumByGoodsId(barterGoodsCollect.getGoodsId());
        return barterGoodsCollectMapper.insert(barterGoodsCollect);
    }

    /**
     * 删除商品收藏
     * @param goodsId,userId
     * @param userId
     * @return
     */
    @Override
    public int goodsCollectDelete(long goodsId,long userId) {
//        barterGoodsInfoMapper.deleteNumByGoodsId(barterGoodsCollectMapper.selectGoodsById(collectId));
        return barterGoodsCollectMapper.deleteById(goodsId,userId);
    }

    @Override
    public long goodsByGoodid(long goodsId) {
        return barterGoodsCollectMapper.countByGoodid(goodsId);
    }

    @Override
    public long goodsByUserid(long userId) {
        return barterGoodsCollectMapper.countByUserid(userId);
    }

    @Override
    public List<BarterGoodsCollect> selectByUserid(long usrId) {
        return barterGoodsCollectMapper.selectByUserid(usrId);
    }

    @Override
    public int collectByGoodsidUserid(long goodsId, long userId) {
        return barterGoodsCollectMapper.collectByGoodsidUserid(goodsId,userId);
    }
}
