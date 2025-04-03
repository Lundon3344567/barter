package com.example.barter_good.common.aop;


import com.example.barter_good.common.util.RedisUtil;
import com.example.barter_good.common.util.SetToListUtil;
import com.example.barter_good.domain.entity.BarterGoodsInfo;
import com.example.barter_good.mapper.BarterGoodsClassifyMapper;
import com.example.barter_good.mapper.BarterGoodsInfoMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chengke
 * @version 1.0
 * @className MyAspect
 * @description AOP拦截切面类,实现访问商品时，该商品浏览量加一
 * @date 2021/11/12 21:05
 */
@Aspect
@Component
public class MyAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SetToListUtil setToListUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BarterGoodsInfoMapper barterGoodsInfoMapper;

    @Autowired
    private BarterGoodsClassifyMapper barterGoodsClassifyMapper;

    /*
    /定义切入点
     */

    @Pointcut("execution(public * com.example.barter_good.controller.GoodsInfoController.queryGoodById(..))")
    public void myPointCut(){

    }

    /*
    以前置通知的方式实现浏览量加一
     */

    @Before("myPointCut()")
    public void doAfter(JoinPoint joinPoint) {

        /*
        通过joinpoint对象获取商品id，然后在redis中更新该商品的浏览量
         */
        Object[] args = joinPoint.getArgs();
        Long id = (Long) args[0];
        redisTemplate.opsForZSet().incrementScore("view_count_id_new",id, 1);

        List<BarterGoodsInfo> barterGoodsInfos = setToListUtil.setToList();

        for (BarterGoodsInfo barterGoodsInfo : barterGoodsInfos) {
            if(barterGoodsInfo.getGoodId().equals(id)){
                barterGoodsInfo.setViewCount(barterGoodsInfo.getViewCount()+1);
            }
        }

        /*
        在redis中更新浏览量
         */
        redisUtil.del("view_count_goods");
        barterGoodsInfos.forEach(bGIs->{
            redisUtil.zSet("view_count_goods", bGIs,bGIs.getViewCount().doubleValue());
        });

        //更新echarts中浏览量
        if(redisUtil.hasKey("echartsView:"+1) == false){
            int a = 0;
        }else{
            redisUtil.set("echartsView:"+getparent(id),(int) redisTemplate.opsForValue().get("echartsView:"+getparent(id)) +1);
        }


    }

    /**
     * 根据分类id查询一级id
     * @param classifyId
     * @return
     */
    private long getparent(long classifyId){
        long pId = barterGoodsInfoMapper.selectClassifiidByGoodId(classifyId);
        while (pId != 0){
            classifyId = pId;
            pId = barterGoodsClassifyMapper.selectparent(classifyId);
        }
        return classifyId;
    }

}