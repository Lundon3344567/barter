package com.example.barter_good.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.example.barter_good.common.util.*;
import com.example.barter_good.domain.entity.BarterGoodsInfo;
import com.example.barter_good.domain.form.InsertGoodsForm;
import com.example.barter_good.domain.form.PageForm;
import com.example.barter_good.interceptor.RedisBloomFilter;
import com.example.barter_good.mapper.BarterGoodsClassifyMapper;
import com.example.barter_good.mapper.BarterGoodsInfoMapper;
import com.example.barter_good.service.GoodsInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ChengKe
 * @description 查询商品信息接口实现类
 * @create 2021/11/9 19:03
 */
@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

//    private static final Logger LOG = LoggerFactory.getLogger(AdServiceImpl.class);

    @Autowired
    private BarterGoodsInfoMapper barterGoodsInfoMapper;

    @Autowired
    private GoodIsNullUtil goodIsNullUtil;

    @Autowired
    private BarterGoodsClassifyMapper barterGoodsClassifyMapper;

    @Autowired
    private UpdateRedisUtil updateRedisUtil;

    @Autowired
    private SetToListUtil setToListUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisBloomFilter redisBloomFilter;



    /**
     * 查询全部商品
     *
     * @return List<BarterGoodsInfo>
     */
    @Override
    public List<BarterGoodsInfo> queryAll() {

        return barterGoodsInfoMapper.queryAll();
    }


    /**
     * 管理员分页查询全部商品
     *
     * @param pageForm pageForm
     * @return PageInfo<BarterGoodsInfo>
     */
    @Override
    public PageInfo<BarterGoodsInfo> queryGoodsByPage(PageForm pageForm) {

        List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

        /*
        若redis中无相应数据，则从数据库中查找，并将查出来的数据添加到redis中
         */
        if(barterGoodsInfoList.size()==0){
            barterGoodsInfoList = barterGoodsInfoMapper.queryAll();
            updateRedisUtil.updateRedisList(barterGoodsInfoList);

        }

        return PageUtil.pageList(pageForm, barterGoodsInfoList);
    }


    /**
     * 管理员根据id查询商品
     *
     * @param id id
     * @return barterGoodsInfo
     */
    @Override
    public BarterGoodsInfo getListById(Long id) {

        /*
        解决缓存穿透
         */
        String str = "S"+id;
        if(!redisBloomFilter.isExist(str)){
            return null;
        }

        /*
        查找缓存中是否存在该商品
         */
        List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

        BarterGoodsInfo barterGoodsInfo = new BarterGoodsInfo();

        for (BarterGoodsInfo goodsInfo : barterGoodsInfoList) {
            if (goodsInfo.getGoodId().equals(id)) {
                barterGoodsInfo = goodsInfo;
            }
        }

        /*
        若redis中无相应数据，则从数据库中查找，并将查出来的数据添加到redis中
         */
        if (barterGoodsInfo.getGoodId() == null) {
            barterGoodsInfo = barterGoodsInfoMapper.queryById(id);
            updateRedisUtil.updateRedisGood(barterGoodsInfo);
        }

        return barterGoodsInfo;
    }

    /**
     * 普通用户根据id查询商品
     *
     * @param id id
     * @return barterGoodsInfo
     */
    @Override
    public BarterGoodsInfo queryGoodById(Long id) {

        /*
        解决缓存穿透
         */
        String str = "S"+id;
        if(!redisBloomFilter.isExist(str)){
            return null;
        }

        /*
        查找缓存中是否存在该商品
         */
        List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

        BarterGoodsInfo barterGoodsInfo = new BarterGoodsInfo();

        for (BarterGoodsInfo goodsInfo : barterGoodsInfoList) {
            if (goodsInfo.getGoodId().equals(id) && goodsInfo.getGoodFlag().equals(0) && goodsInfo.getGoodNum()>0 && goodsInfo.getOwnerFlag().equals(1)) {
                barterGoodsInfo = goodsInfo;
            }
        }

        /*
        若redis中无相应数据，则从数据库中查找，并将查出来的数据添加到redis中
         */
        if (barterGoodsInfo.getGoodId() == null) {
            BarterGoodsInfo barterGoodsInfoNew = barterGoodsInfoMapper.queryById(id);

            //如果该商品正在售卖且库存大于0且商家状态正常，则返回该商品用于展示
            if(barterGoodsInfoNew.getGoodFlag().equals(0) && barterGoodsInfoNew.getGoodNum()>0 && barterGoodsInfoNew.getOwnerFlag().equals(1)){
                barterGoodsInfo = barterGoodsInfoNew;
            }else{
                //若不满足上述条件，则将goodID设为-2用于判断
                barterGoodsInfo.setGoodId((long) -2);
            }

        }

        return barterGoodsInfo;
    }


    /**
     * 管理员插入商品
     *
     * @param insertGoodsForm insertGoodsForm
     * @return rows
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public synchronized int adGoods(InsertGoodsForm insertGoodsForm) {

        BarterGoodsInfo barterGoodsInfo = new BarterGoodsInfo();

        Date date = new Date();

        barterGoodsInfo.setOwnerId(insertGoodsForm.getOwnerId());
        barterGoodsInfo.setOwnerFlag(1);
        barterGoodsInfo.setGoodName(insertGoodsForm.getGoodName());
        barterGoodsInfo.setGoodPrice(insertGoodsForm.getGoodPrice());
        barterGoodsInfo.setGoodFlag(1);
        barterGoodsInfo.setImgUrl(insertGoodsForm.getImgUrl());
        barterGoodsInfo.setGoodDescribe(insertGoodsForm.getGoodDescribe());
        barterGoodsInfo.setCollectionNum(0);
        barterGoodsInfo.setClassifiedNum(insertGoodsForm.getClassifiedNum());
        barterGoodsInfo.setGoodNum(insertGoodsForm.getGoodNum());
        barterGoodsInfo.setViewCount(0);
        barterGoodsInfo.setCreateTime(date);

        int rows = barterGoodsInfoMapper.insert(barterGoodsInfo);

        /*
        将插入的商品信息更新到redis中
         */
        redisBloomFilter.put(String.valueOf(barterGoodsInfo.getGoodId()));
        redisUtil.zSet("view_count_goods", barterGoodsInfo,barterGoodsInfo.getViewCount().doubleValue());


        if (rows != 1) {
            return 0;
        }
        return rows;
    }


    /**
     * 管理员更新商品信息
     *
     * @param barterGoodsInfo barterGoodsInfo
     * @return int
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public synchronized int updateGoods(BarterGoodsInfo barterGoodsInfo) {
        int rows = barterGoodsInfoMapper.update(barterGoodsInfo);

        /*
        更新redis中的对应商品信息
         */
        List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

        for (BarterGoodsInfo info : barterGoodsInfoList) {
            if (info.getGoodId().equals(barterGoodsInfo.getGoodId())) {
                goodIsNullUtil.updateGoodInList(barterGoodsInfo, info);
            }
        }

        /*
        将更新的商品信息保存到redis中,延迟双删保证数据一致性
         */
        redisUtil.del("view_count_goods");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
        }
        redisUtil.del("view_count_goods");
        for (BarterGoodsInfo goodsInfo : barterGoodsInfoList) {
            redisUtil.zSet("view_count_goods", goodsInfo,goodsInfo.getViewCount().doubleValue());
        }


        if (rows != 1) {
            return 0;
        }
        return 1;
    }


    /**
     * 管理员删除商品信息
     *
     * @param id id
     * @return int
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public synchronized int deleteGoods(Long id) {

        BarterGoodsInfo barterGoodsInfo = new BarterGoodsInfo();

        barterGoodsInfo.setGoodId(id);
        barterGoodsInfo.setGoodFlag(-2);

        int rows = barterGoodsInfoMapper.update(barterGoodsInfo);
        /*
        更新redis中的对应商品信息
         */

        List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

        for (int i = 0; i < barterGoodsInfoList.size(); i++) {
            if(barterGoodsInfoList.get(i).getGoodId().equals(id)){
                barterGoodsInfoList.get(i).setGoodFlag(-2);
                i--;
            }
        }

        /*
        将删除的商品信息在redis中对应的标志位设置为-2
         */
        redisUtil.del("view_count_goods");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
        }
        redisUtil.del("view_count_goods");
        for (BarterGoodsInfo goodsInfo : barterGoodsInfoList) {
            redisUtil.zSet("view_count_goods", goodsInfo,goodsInfo.getViewCount().doubleValue());
        }

        if (rows != 1) {
            return 0;
        }
        return 1;
    }


    /**
     * 普通用户获取推荐商品
     *
     * @param pageForm pageForm
     * @return PageInfo<BarterGoodsInfo>
     */
    @Override
    public PageInfo<BarterGoodsInfo> recommendGoods(PageForm pageForm) {

        List<BarterGoodsInfo> barterGoodsInfos = setToListUtil.setToList();

        Collections.reverse(barterGoodsInfos);

        List<BarterGoodsInfo> barterGoodsInfosNew = new ArrayList<>();

        for (BarterGoodsInfo barterGoodsInfo : barterGoodsInfos) {
            if(barterGoodsInfo.getGoodFlag().equals(0) && barterGoodsInfo.getGoodNum()>0 && barterGoodsInfo.getOwnerFlag().equals(1)){
                barterGoodsInfosNew.add(barterGoodsInfo);
            }
        }

        /*
        若redis中无相应数据，则从数据库中查找，并将查出来的数据添加到redis中
         */
        if(barterGoodsInfos.size() == 0){
            barterGoodsInfos = barterGoodsInfoMapper.queryByViewCount();
            for (BarterGoodsInfo barterGoodsInfo : barterGoodsInfos) {
                if(barterGoodsInfo.getGoodFlag().equals(0) && barterGoodsInfo.getGoodNum()>0 && barterGoodsInfo.getOwnerFlag().equals(1)){
                    barterGoodsInfosNew.add(barterGoodsInfo);
                }
            }
            updateRedisUtil.updateRedisList(barterGoodsInfos);
        }

        return PageUtil.pageList(pageForm,barterGoodsInfosNew);
    }


    /**
     * 普通用户模糊搜索商品
     *
     * @param pageForm pageForm
     * @param goodName goodName
     * @return PageInfo<BarterGoodsInfo>
     */
    @Override
    public PageInfo<BarterGoodsInfo> queryByName(PageForm pageForm, String goodName) {

        /*
        借助正则表达式模糊匹配字符串
         */
        String regexContainStr = "^(.*" + PatternUtil.escapeExprSpecialWord(goodName) + ".*)$";

        List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

        List<BarterGoodsInfo> barterGoodsInfoListName = new ArrayList<>();

        for (BarterGoodsInfo barterGoodsInfo : barterGoodsInfoList) {
            if (barterGoodsInfo.getGoodName().matches(regexContainStr) && barterGoodsInfo.getGoodFlag().equals(0) && barterGoodsInfo.getGoodNum()>0 && barterGoodsInfo.getOwnerFlag().equals(1)) {
                barterGoodsInfoListName.add(barterGoodsInfo);
            }
        }

        /*
        若redis中无相应数据，则从数据库中查找，并将查出来的数据添加到redis中
         */
        if (barterGoodsInfoListName.size() == 0) {
            List<BarterGoodsInfo> barterGoodsInfoLists = barterGoodsInfoMapper.queryAll();
            for (BarterGoodsInfo barterGoodsInfo : barterGoodsInfoLists) {
                if(barterGoodsInfo.getGoodName().matches(regexContainStr) && barterGoodsInfo.getGoodFlag().equals(0) && barterGoodsInfo.getGoodNum()>0 && barterGoodsInfo.getOwnerFlag().equals(1)){
                    barterGoodsInfoListName.add(barterGoodsInfo);
                }
            }
            updateRedisUtil.updateRedisList(barterGoodsInfoLists);

        }

        return PageUtil.pageList(pageForm, barterGoodsInfoListName);
    }


    /**
     * 管理员模糊搜索商品
     *
     * @param pageForm pageForm
     * @param goodName goodName
     * @return PageInfo<BarterGoodsInfo>
     */
    @Override
    public PageInfo<BarterGoodsInfo> adQueryByName(PageForm pageForm, String goodName) {

        /*
        借助正则表达式模糊匹配字符串
         */

        String regexContainStr = "^(.*" + PatternUtil.escapeExprSpecialWord(goodName) + ".*)$";

        List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

        List<BarterGoodsInfo> barterGoodsInfoListName;

        barterGoodsInfoListName = barterGoodsInfoList.stream()
                                                     .filter(s->s.getGoodName().matches(regexContainStr))
                                                     .collect(Collectors.toList());

        /*
        若redis中无相应数据，则从数据库中查找，并将查出来的数据添加到redis中
         */
        if (barterGoodsInfoListName.size() == 0) {
            List<BarterGoodsInfo> barterGoodsInfoLists = barterGoodsInfoMapper.queryAll();
            barterGoodsInfoListName = barterGoodsInfoLists.stream()
                                                          .filter(s->s.getGoodName().matches(regexContainStr))
                                                          .collect(Collectors.toList());
            updateRedisUtil.updateRedisList(barterGoodsInfoLists);

        }

        return PageUtil.pageList(pageForm, barterGoodsInfoListName);
    }

    /**
     * 普通用户按创建时间排序商品
     *
     * @param pageForm pageform
     * @return PageInfo<BarterGoodsInfo>
     */
    @Override
    public PageInfo<BarterGoodsInfo> queryGoodsOrderByTime(PageForm pageForm) {

        List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

        List<BarterGoodsInfo> barterGoodsInfoListNew = new ArrayList<>();

        /*
        通过collection内部类排序商品的创建时间
         */
        barterGoodsInfoList.sort((o1, o2) -> {
            if (o1.getCreateTime().after(o2.getCreateTime())) {
                return -1;
            } else if (o1.getCreateTime().before(o2.getCreateTime())) {
                return 1;
            }
            return 0;
        });

        for (BarterGoodsInfo barterGoodsInfo : barterGoodsInfoList) {
            if(barterGoodsInfo.getGoodFlag().equals(0) && barterGoodsInfo.getGoodNum()>0 && barterGoodsInfo.getOwnerFlag().equals(1)){
                barterGoodsInfoListNew.add(barterGoodsInfo);
            }
        }

        /*
        若redis中无相应数据，则从数据库中查找，并将查出来的数据添加到redis中
         */
        if (barterGoodsInfoList.size() == 0) {
            List<BarterGoodsInfo> barterGoodsInfoLists = barterGoodsInfoMapper.queryByTime();
            for (BarterGoodsInfo barterGoodsInfo : barterGoodsInfoLists) {
                if(barterGoodsInfo.getGoodFlag().equals(0) && barterGoodsInfo.getGoodNum()>0 && barterGoodsInfo.getOwnerFlag().equals(1)){
                    barterGoodsInfoListNew.add(barterGoodsInfo);
                }
            }
            updateRedisUtil.updateRedisList(barterGoodsInfoLists);
        }

        return PageUtil.pageList(pageForm, barterGoodsInfoListNew);
    }


    /**
     * 普通用户根据分类筛选商品
     *
     * @param pageForm pageForm
     * @param id id
     * @return PageInfo<BarterGoodsInfo>
     */
    @Override
    public PageInfo<BarterGoodsInfo> queryByClassification(PageForm pageForm, Long id) {

        List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

        List<BarterGoodsInfo> collect = new ArrayList<>();

        List<BarterGoodsInfo> collectAll = new ArrayList<>();

        /*
        判断是否是一级分类
         */
        List<Long> idList = barterGoodsClassifyMapper.queryThirdLevelId(id);

        if(idList.size()==0){
            /*
            如果是二级分类，查找的是该分类下正常出售且商品数量大于0的
            */

            for (BarterGoodsInfo barterGoodsInfo : barterGoodsInfoList) {
                if(barterGoodsInfo.getClassifiedNum().equals(id) && barterGoodsInfo.getGoodFlag().equals(0) && barterGoodsInfo.getGoodNum()>0 && barterGoodsInfo.getOwnerFlag().equals(1)){
                    collect.add(barterGoodsInfo);
                }
            }
        }else{
            /*
            如果是一级分类
             */
            for (Long aLong : idList) {
                for (BarterGoodsInfo barterGoodsInfo : barterGoodsInfoList) {
                    if(barterGoodsInfo.getClassifiedNum().equals(aLong) && barterGoodsInfo.getGoodFlag().equals(0) && barterGoodsInfo.getGoodNum()>0 && barterGoodsInfo.getOwnerFlag().equals(1)){
                        collect.add(barterGoodsInfo);
                    }
                }
            }

        }

        /*
        若redis中无相应数据，则从数据库中查找，并将查出来的数据添加到redis中

        如果在redis中查找该分类下所有商品都没有，则从数据库中查出该分类下所有商品
        然后将正常出售和商品数量大于0的返回
        最后将查出的该分类下所有商品存入redis
         */
        for (BarterGoodsInfo barterGoodsInfo : barterGoodsInfoList) {
            if(barterGoodsInfo.getClassifiedNum().equals(id)){
                collectAll.add(barterGoodsInfo);
            }
        }
        if(collectAll.size()==0){
            collectAll = barterGoodsInfoMapper.queryAll();
            for (BarterGoodsInfo barterGoodsInfo : collectAll) {
                if(barterGoodsInfo.getClassifiedNum().equals(id) && barterGoodsInfo.getGoodFlag().equals(0) && barterGoodsInfo.getGoodNum()>0 && barterGoodsInfo.getOwnerFlag().equals(1)){
                    collect.add(barterGoodsInfo);
                }
            }
            updateRedisUtil.updateRedisList(collectAll);
        }


        return PageUtil.pageList(pageForm, collect);
    }


    /**
     * 普通用户根据收藏数排序商品
     *
     * @param pageForm pageform
     * @return PageInfo<BarterGoodsInfo>
     */
    @Override
    public PageInfo<BarterGoodsInfo> queryByCollection(PageForm pageForm) {

        List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

        BarterGoodsInfo barterGoodsInfo;

        for (int i = barterGoodsInfoList.size() - 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                /*
                从大到小排序
                 */
                if (barterGoodsInfoList.get(j + 1).getCollectionNum() > barterGoodsInfoList.get(j).getCollectionNum()) {
                    barterGoodsInfo = barterGoodsInfoList.get(j);
                    barterGoodsInfoList.set(j, barterGoodsInfoList.get(j + 1));
                    barterGoodsInfoList.set(j + 1, barterGoodsInfo);
                }
            }
        }

        /*
        若redis中无相应数据，则从数据库中查找，并将查出来的数据添加到redis中
         */
        if (barterGoodsInfoList.size() == 0) {
            barterGoodsInfoList = barterGoodsInfoMapper.queryByCollection();
            updateRedisUtil.updateRedisList(barterGoodsInfoList);
        }

        return PageUtil.pageList(pageForm, barterGoodsInfoList);

    }


    /**
     * 普通用户根据价格排序商品
     *
     * @param pageForm pageForm
     * @return PageInfo<BarterGoodsInfo>
     */
    @Override
    public PageInfo<BarterGoodsInfo> queryByPrice(PageForm pageForm) {

        List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

        BarterGoodsInfo barterGoodsInfo;

        for (int i = barterGoodsInfoList.size() - 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                /*
                从大到小排序
                 */
                if (barterGoodsInfoList.get(j + 1).getGoodPrice() > barterGoodsInfoList.get(j).getGoodPrice()) {
                    barterGoodsInfo = barterGoodsInfoList.get(j);
                    barterGoodsInfoList.set(j, barterGoodsInfoList.get(j + 1));
                    barterGoodsInfoList.set(j + 1, barterGoodsInfo);
                }
            }
        }

        /*
        若redis中无相应数据，则从数据库中查找，并将查出来的数据添加到redis中
         */
        if (barterGoodsInfoList.size() == 0) {
            barterGoodsInfoList = barterGoodsInfoMapper.queryByPrice();
            updateRedisUtil.updateRedisList(barterGoodsInfoList);
        }

        return PageUtil.pageList(pageForm, barterGoodsInfoList);

    }

    /**
     * 普通用户根据组合条件筛选商品
     *
     * @param pageForm pageform
     * @param id id
     * @param name name
     * @param collectionType collectiontype
     * @param priceType pricetype
     * @param timeType timetype
     * @param viewCountType viewcounttype
     * @return PageInfo<BarterGoodsInfo>
     */
    @Override
    public PageInfo<BarterGoodsInfo> queryByAll(PageForm pageForm, Long id, String name, Integer collectionType, Integer priceType, Integer timeType, Integer viewCountType) {

        PageHelper.startPage(pageForm.getRequestPage(), pageForm.getPageSize());

        List<BarterGoodsInfo> barterGoodsInfos = barterGoodsInfoMapper.queryByAll(id, name, collectionType, priceType, timeType, viewCountType);

        return new PageInfo<>(barterGoodsInfos);
    }

    /**
     * 订单查库存
     * @param goodsId 商品id
     * @return 是否有库存
     */
    @Override
    public Boolean queryGoodsNum(Long goodsId){
        boolean flag = false;
        int goodsNum = barterGoodsInfoMapper.queryGoodsNum(goodsId);
        if(goodsNum>0){
            flag = true;
        }
        return flag;
    }


    /**
     * 商品加库存
     * @param goodsId 商品id
     * @param num 数量
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public synchronized Boolean increaseGoodsNum(Long goodsId,Integer num){
        boolean flag = false;
        int row = barterGoodsInfoMapper.updateAddNum(goodsId,num);

        /*
        更新redis中的对应商品信息
         */
        List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

        for (BarterGoodsInfo info : barterGoodsInfoList) {
            if (info.getGoodId().equals(goodsId)) {
                info.setGoodNum(info.getGoodNum()+num);
            }
        }

        /*
        将更新的商品信息保存到redis中,延迟双删保证数据一致性
        延时的根本目的就是为了让程序先把【新查到的数据写入redis】执行完，再去删除redis
         */
        redisUtil.del("view_count_goods");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
        }
        redisUtil.del("view_count_goods");
        for (BarterGoodsInfo goodsInfo : barterGoodsInfoList) {
            redisUtil.zSet("view_count_goods", goodsInfo,goodsInfo.getViewCount().doubleValue());
        }

        if(row>0){
            flag = true;
        }
        return flag;
    }

    /**
     * 商品减库存
     * @param goodsId 商品id
     * @param num 数量
     */
    @LcnTransaction
    @Transactional(rollbackFor=Exception.class)
    @Override
    public synchronized Boolean decreaseGoodsNum(Long goodsId,Integer num){
        boolean flag = false;
        int row = barterGoodsInfoMapper.updateSubtract(goodsId,num);

        if(row>0){
            /*
            更新redis中的对应商品信息
            */
            List<BarterGoodsInfo> barterGoodsInfoList = setToListUtil.setToList();

            for (BarterGoodsInfo info : barterGoodsInfoList) {
                if (info.getGoodId().equals(goodsId)) {
                    info.setGoodNum(info.getGoodNum()+num);
                }
            }

            /*
            将更新的商品信息保存到redis中,延迟双删保证数据一致性
            延时的根本目的就是为了让程序先把【新查到的数据写入redis】执行完，再去删除redis
             */
            redisUtil.del("view_count_goods");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus()
                        .setRollbackOnly();
            }
            redisUtil.del("view_count_goods");
            for (BarterGoodsInfo goodsInfo : barterGoodsInfoList) {
                redisUtil.zSet("view_count_goods", goodsInfo,goodsInfo.getViewCount().doubleValue());
            }

            flag = true;
        }

        return flag;
    }

    /**
     * 查询用户拥有的商品
     * @param pageForm 分页参数
     * @param userId 用户id
     * @param type 查询类型
     * @return 商品
     */
    @Override
    public PageInfo<BarterGoodsInfo> queryGoodsByUserId(PageForm pageForm, long userId, int type) {

        //type 0：正常（审核通过，未售出）   -1：已售出   1：审核中   2：已冻结  100：审核不通过   1000：查询全部

        PageHelper.startPage(pageForm.getRequestPage(),pageForm.getPageSize());
        if(type == 1000){
            List<BarterGoodsInfo> barterGoodsInfoList = barterGoodsInfoMapper.queryByUserIdAll(userId);
            return new PageInfo<>(barterGoodsInfoList);
        }else{
            List<BarterGoodsInfo> barterGoodsInfoList = barterGoodsInfoMapper.queryByUserId(userId,type);
            return new PageInfo<>(barterGoodsInfoList);
        }

    }
}