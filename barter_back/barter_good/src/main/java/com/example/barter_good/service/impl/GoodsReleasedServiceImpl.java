package com.example.barter_good.service.impl;

import com.example.barter_good.common.util.RedisUtil;
import com.example.barter_good.domain.entity.BarterGoodsClassify;
import com.example.barter_good.domain.entity.BarterGoodsInfo;
import com.example.barter_good.domain.form.GoodsReleasedFrom;
import com.example.barter_good.domain.form.PageForm;
import com.example.barter_good.mapper.BarterGoodsClassifyMapper;
import com.example.barter_good.mapper.BarterGoodsInfoMapper;
import com.example.barter_good.service.GoodsInfoService;
import com.example.barter_good.service.GoodsReleasedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName GoodsReleasedServiceImpl
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-13 16:44
 * @Version 1.0
 **/
@Service
public class GoodsReleasedServiceImpl implements GoodsReleasedService {
    @Autowired
    private BarterGoodsClassifyMapper barterGoodsClassifyMapper;

    @Autowired
    private BarterGoodsInfoMapper barterGoodsInfoMapper;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据父Id列出所有子分类
     * @param parentId
     * @return barterGoodsClassifyList
     */
    @Override
    public List<BarterGoodsClassify> selectClassifyByParentId(long parentId) {
        List<BarterGoodsClassify>barterGoodsClassifyList = barterGoodsClassifyMapper.selectByParentId(parentId);
        return barterGoodsClassifyList;
    }

    @Override
    public PageInfo<BarterGoodsInfo> queryByParentId(PageForm pageForm, long parentId) {
        PageHelper.startPage(pageForm.getRequestPage(),pageForm.getPageSize());
        List<BarterGoodsInfo> barterAdInfoList = barterGoodsInfoMapper.queryByParentId(parentId);
        return new PageInfo<BarterGoodsInfo>(barterAdInfoList);
    }

    /**
     * 递归分类树
     * @return
     */
    @Override
    public List<BarterGoodsClassify> selectTree() {
        List<BarterGoodsClassify>barterGoodsClassifyList = barterGoodsClassifyMapper.selectTree();
        List<BarterGoodsClassify>treeList = new ArrayList<>();
        //找所有的一级分类
        for(BarterGoodsClassify barterGoodsClassify :barterGoodsClassifyList){
            if(barterGoodsClassify.getParentId() == 0 ){
                treeList.add(barterGoodsClassify);
            }
        }
        for(BarterGoodsClassify barterGoodsClassify :treeList){
            barterGoodsClassify.setChildren(getchilde(barterGoodsClassify.getClassifyId(),barterGoodsClassifyList));
        }
        return treeList;
    }

    /**
     * 添加分类
     * @param barterGoodsClassify barterGoodsClassify
     * @return int
     */
    @Override
    public int addClassify(BarterGoodsClassify barterGoodsClassify) {
        return barterGoodsClassifyMapper.insert(barterGoodsClassify);
    }

    /**
     * 删除分类
     * @param classifyId classifyId
     * @return int
     */
    @Override
    public int deleteClassifyById(long classifyId) {

        PageForm pageForm = new PageForm(1,5);

        PageInfo<BarterGoodsInfo> barterGoodsInfoList = goodsInfoService.queryByClassification(pageForm,classifyId);

        if(barterGoodsInfoList.getSize() != 0){
            return -1;
        }

        return barterGoodsClassifyMapper.deleteById(classifyId);
    }

    /**
     * 更新分类
     * @param barterGoodsClassify barterGoodsClassify
     * @return int
     */
    @Override
    public int updateClassify(BarterGoodsClassify barterGoodsClassify) {
        return barterGoodsClassifyMapper.update(barterGoodsClassify);
    }

    @Override
    public BarterGoodsClassify selectClassifyById(long classifyId) {
        return barterGoodsClassifyMapper.queryById(classifyId);
    }

    /**
     * 商品发布
     * @param goodsReleasedFrom
     * @return
     */
    @Override
    public int addGoodsRelease(GoodsReleasedFrom goodsReleasedFrom) {
        BarterGoodsInfo barterGoodsInfo = new BarterGoodsInfo();
        BeanUtils.copyProperties(goodsReleasedFrom,barterGoodsInfo,BarterGoodsInfo.class);
        barterGoodsInfo.setOwnerFlag(1);
        barterGoodsInfo.setGoodFlag(1);
        barterGoodsInfo.setCreateTime(new Date());
        barterGoodsInfo.setCollectionNum(0);
        barterGoodsInfo.setViewCount(0);
        if(barterGoodsInfoMapper.insert(barterGoodsInfo) <= 0){
            return 0;
        }
        //更新echarts中近周的发布量
        if(redisUtil.hasKey("echartsReleased:"+1) == false){
            int a = 0;
        }else{
            redisUtil.set("echartsReleased:"+getparent(barterGoodsInfo.getGoodId()),(int) redisTemplate.opsForValue().get("echartsReleased:"+getparent(barterGoodsInfo.getGoodId())) +barterGoodsInfo.getGoodNum());
        }

        return 1;
    }

    /**
     * 孩子列表
     * @param classifyId
     * @param barterGoodsClassifyList
     * @return
     */
    private List<BarterGoodsClassify> getchilde(Long classifyId, List<BarterGoodsClassify> barterGoodsClassifyList) {
        List<BarterGoodsClassify> childList = new ArrayList<>();
        for(BarterGoodsClassify barterGoodsClassify :barterGoodsClassifyList){
            if(barterGoodsClassify.getParentId().equals(classifyId)){
                childList.add(barterGoodsClassify);
            }
        }
        for(BarterGoodsClassify barterGoodsClassify :childList){
            barterGoodsClassify.setChildren(getchilde(barterGoodsClassify.getClassifyId(),barterGoodsClassifyList));
        }
        if(childList.size() == 0){
            return null;
        }
        return childList;
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
