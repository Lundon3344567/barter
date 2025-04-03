package com.example.barter_good.common.aop;


import com.example.barter_good.common.util.RedisUtil;
import com.example.barter_good.domain.entity.BarterGoodsInfo;
import com.example.barter_good.interceptor.RedisBloomFilter;
import com.example.barter_good.service.GoodsInfoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author chengke
 * @version 1.0
 * @className RedisTask
 * @description 定时更新redis
 * @date 2021/11/23 9:51
 */
@Slf4j
@Async
@Component
public class RedisTask extends QuartzJobBean {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisBloomFilter redisBloomFilter;

    @Autowired
    private RedisTemplate redisTemplate;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 定时将redis中的数据写入数据库，并将新数据更新到redis，时间间隔1小时.
     * @param context context
     */
    @SneakyThrows
    @Override
    public void executeInternal(JobExecutionContext context) {
        logger.info("开始将redis中的数据写入数据库",sdf.format(new Date()));

        /*
        将redis中的浏览量同步到数据库中
         */
        Set<DefaultTypedTuple> viewCountId = redisUtil.getZSetRank("view_count_id", 0, -1);
        Set<DefaultTypedTuple> viewCountIdNew = redisUtil.getZSetRank("view_count_id_new", 0, -1);

        viewCountId.forEach(vci->{
            viewCountIdNew.forEach(vcin->{
                if (Objects.equals(vcin.getValue(), vci.getValue())){
                    if(Objects.equals(vcin.getScore(), vci.getScore())){

                    }else{
                        BarterGoodsInfo barterGoodsInfo = new BarterGoodsInfo();
                        barterGoodsInfo.setGoodId(Long.valueOf(String.valueOf(vcin.getValue())));
                        barterGoodsInfo.setViewCount(vcin.getScore().intValue());
                        goodsInfoService.updateGoods(barterGoodsInfo);
                    }
                }
            });
        });

        logger.info("存入数据库成功",sdf.format(new Date()));

        logger.info("=======================================================");

        //将数据库中的数据更新入redis中
        logger.info("将数据库中的数据更新到redis中",sdf.format(new Date()));
        Set keys = redisTemplate.keys("S" + "*");
        /*执行删除*/
        redisTemplate.delete(keys);
        redisUtil.del("view_count_id");
        redisUtil.del("view_count_id_new");
        redisUtil.del("view_count_goods");


        List<BarterGoodsInfo> goodsInfoList = goodsInfoService.queryAll();
        goodsInfoList.forEach(barterGoodsInfo -> {
            //初始化布隆过滤器（放入商品id）,加上S前缀便于删除
            String str = "S"+barterGoodsInfo.getGoodId();
            redisBloomFilter.put(str);

            redisUtil.zSet("view_count_id",barterGoodsInfo.getGoodId(),barterGoodsInfo.getViewCount().doubleValue());
            redisUtil.zSet("view_count_id_new",barterGoodsInfo.getGoodId(),barterGoodsInfo.getViewCount().doubleValue());
            redisUtil.zSet("view_count_goods", barterGoodsInfo,barterGoodsInfo.getViewCount().doubleValue());
        });

        logger.info("已将数据更新到redis",sdf.format(new Date()));
    }
}