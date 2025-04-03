package com.barter.adservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.barter.adservice.domain.entity.BarterAdInfo;
import com.barter.adservice.domain.form.PageForm;
import com.barter.adservice.mapper.BarterAdInfoMapper;
import com.barter.adservice.service.AdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @ClassName AdServiceImpl
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-10 13:05
 * @Version 1.0
 **/
@Service
public class AdServiceImpl implements AdService {
    private static final Logger LOG = LoggerFactory.getLogger(com.barter.adservice.service.impl.AdServiceImpl.class);

    @Autowired
    private BarterAdInfoMapper barterAdInfoMapper;

//    @Autowired
//    private RedisTemplate redisTemplate1;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @CacheEvict(value = "ad",allEntries = true)
    @Override
    public int adAdd(BarterAdInfo barterAdInfo){

        Date date = new Date();
        barterAdInfo.setCreateTime(new Date());

        barterAdInfo.setLastEditTime(new Date());
        //暂时的
        barterAdInfo.setVerifyTime(new Date());

        //暂时的
        barterAdInfo.setAuditId((long) 2);

        //刚插入时默认审核状态为：审核中 0
        barterAdInfo.setAdState(0);

        int rows = barterAdInfoMapper.insert(barterAdInfo);
        long time = DateConversion(barterAdInfo.getCreateTime(),barterAdInfo.getAdValidity());
        redisTemplate.opsForValue().set("adInfo:"+barterAdInfo.getAdId(),barterAdInfo.getAdId()+"",time, TimeUnit.SECONDS);
        LOG.info("LOG==> " + String.format("%-12s", "barterAdInfo") + String.format("%-12s", barterAdInfo) + " :: info: 创建广告");
        if(rows <= 0){
            return 0;
        }
        return 1;
    }

    @CacheEvict(value = "ad",allEntries = true)
    @Override
    public int adDeleteById(long adId){
        int rows = barterAdInfoMapper.deleteById(adId);
        LOG.info("LOG==> " + String.format("%-12s", "barterAdInfo") + String.format("%-12s", adId) + " :: info: 删除广告");
        if(rows <= 0){
            return 0;
        }
        return 1;
    }

    @CacheEvict(value = "ad",allEntries = true)
    @Override
    public int adModify(BarterAdInfo barterAdInfo){
        barterAdInfo.setLastEditTime(new Date());

        int rows = barterAdInfoMapper.update(barterAdInfo);
        LOG.info("LOG==> " + String.format("%-12s", "barterAdInfo") + String.format("%-12s", barterAdInfo) + " :: info: 更新广告");
        if(rows <= 0){
            return 0;
        }
        return 1;

    }

    @Cacheable(value = "ad",key = "'queryAdByPageAll'+#pageForm.requestPage+','+#pageForm.pageSize")
    @Override
    public PageInfo<BarterAdInfo> queryAdByPageAll(PageForm pageForm) {
        PageHelper.startPage(pageForm.getRequestPage(),pageForm.getPageSize());
        List<BarterAdInfo> barterAdInfoList = barterAdInfoMapper.queryAll();
        return new PageInfo<BarterAdInfo>(barterAdInfoList);
    }

    @Override
    public PageInfo<BarterAdInfo> adSelectOrderBy(PageForm pageForm) {
        PageHelper.startPage(pageForm.getRequestPage(),pageForm.getPageSize());
        List<BarterAdInfo> barterAdInfoList = barterAdInfoMapper.adSelectOrderBy();
        return new PageInfo<BarterAdInfo>(barterAdInfoList);
    }

    @Override
    public List<BarterAdInfo> adSelect() {
        List<BarterAdInfo> barterAdInfoList = barterAdInfoMapper.adSelect();
        return barterAdInfoList;
    }

    @Override
    public BarterAdInfo selectAdById(long adId) {
        BarterAdInfo barterAdInfo = barterAdInfoMapper.queryById(adId);
        return barterAdInfo;
    }

    public long DateConversion(Date date1,Date date2){
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        return (time2-time1)/1000;
    }
}
