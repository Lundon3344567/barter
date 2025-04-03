package com.example.barter_good.controller;

import com.example.barter_good.common.util.ResultInfoUtil;
import com.example.barter_good.domain.entity.BarterGoodsInfo;
import com.example.barter_good.domain.form.GoodsForm;
import com.example.barter_good.domain.form.PageForm;
import com.example.barter_good.lang.ResultInfo;
import com.example.barter_good.service.GoodsInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chengke
 * @version 1.0
 * @className GoodsInfoController
 * @description 普通用户商品接口
 * @date 2021/11/14 22:16
 */
@RestController
@RequestMapping("/goods")
@Api(tags = "goods-released-controller")
public class GoodsInfoController {


    @Autowired
    private GoodsInfoService goodsInfoService;


    /**
     * 普通用户获取推荐商品信息
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return ResultInfo
     */

    @ApiOperation("获取推荐商品信息(浏览量)")
    @GetMapping(value = "/recommend")
    public ResultInfo recommendGoods(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize
    ){
        PageForm pageForm = new PageForm(pageNum, pageSize);

        PageInfo<BarterGoodsInfo> map = goodsInfoService.recommendGoods(pageForm);

        return ResultInfoUtil.buildSuccess(map);
    }


    /**
     * 普通用户模糊查询商品
     * @param goodName goodName
     * @param pageSize pageSize
     * @param pageNum pageNum
     * @return ResultInfo
     */
    @ApiOperation("模糊查询商品")
    @GetMapping("/name")
    public ResultInfo queryAllGoods(@RequestParam("name") String goodName,
                                    @RequestParam("pageSize") Integer pageSize,
                                    @RequestParam("pageNum") Integer pageNum){

        GoodsForm goodsForm = new GoodsForm();
        goodsForm.setGoodName(goodName);
        goodsForm.setPageSize(pageSize);
        goodsForm.setPageNum(pageNum);

        PageForm pageForm = new PageForm(goodsForm.getPageNum(), goodsForm.getPageSize());

        PageInfo<BarterGoodsInfo> map = goodsInfoService.queryByName(pageForm,goodsForm.getGoodName());

        return ResultInfoUtil.buildSuccess(map);
    }

    /**
     * 普通用户按创建时间排序商品(查看最新发布商品)
     * @param pageSize pageSize
     * @param pageNum pageNum
     * @return ResultInfo
     */
    @ApiOperation("按创建时间排序商品")
    @GetMapping("/time")
    public ResultInfo queryGoodsOrderByTime(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNum") Integer pageNum){

        PageForm pageForm = new PageForm(pageNum,pageSize);

        PageInfo<BarterGoodsInfo> map = goodsInfoService.queryGoodsOrderByTime(pageForm);

        return ResultInfoUtil.buildSuccess(map);
    }

    /**
     * 根据分类筛选商品
     * @param pageSize pageSize
     * @param pageNum pageNum
     * @param id id
     * @return ResultInfo
     */
    @ApiOperation("根据分类筛选商品")
    @GetMapping("/classify")
    public ResultInfo queryGoodsOrderByClassification(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("id") Long id){

        PageForm pageForm = new PageForm(pageNum,pageSize);

        PageInfo<BarterGoodsInfo> barterGoodsInfoList = goodsInfoService.queryByClassification(pageForm,id);

        return ResultInfoUtil.buildSuccess(barterGoodsInfoList);
    }

    /**
     * 根据收藏数排序商品
     * @param pageSize pageSize
     * @param pageNum pageNum
     * @return ResultInfo
     */
    @ApiOperation("根据收藏数排序商品")
    @GetMapping("/collection")
    public ResultInfo queryGoodsOrderByCollection(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNum") Integer pageNum){

        PageForm pageForm = new PageForm(pageNum,pageSize);

        PageInfo<BarterGoodsInfo> map = goodsInfoService.queryByCollection(pageForm);

        return ResultInfoUtil.buildSuccess(map);
    }

    /**
     * 根据价格排序商品
     * @param pageSize pageSize
     * @param pageNum pageNum
     * @return ResultInfo
     */
    @ApiOperation("根据价格排序商品")
    @GetMapping("/price")
    public ResultInfo queryGoodsOrderByPrice(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNum") Integer pageNum){

        PageForm pageForm = new PageForm(pageNum,pageSize);

        PageInfo<BarterGoodsInfo> map = goodsInfoService.queryByPrice(pageForm);

        return ResultInfoUtil.buildSuccess(map);
    }


    /**
     * 根据组合条件筛选商品
     * @param pageSize pageSize
     * @param pageNum pageNum
     * @param classifyId classifyId
     * @param goodName goodName
     * @param collectionType collectionType
     * @param priceType priceType
     * @param timeType timeType
     * @param viewCountType viewCountType
     * @return ResultInfo
     */
    @ApiOperation("综合排序商品")
    @GetMapping("/id/name/type")
    public ResultInfo queryGoodsOrderByAll(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam(value = "classifyId",required = false) Long classifyId,
            @RequestParam(value = "goodName",required = false) String goodName,
            @RequestParam(value = "collectionType",required = false) Integer collectionType,
            @RequestParam(value = "priceType",required = false) Integer priceType,
            @RequestParam(value = "timeType",required = false) Integer timeType,
            @RequestParam(value = "viewCountType",required = false) Integer viewCountType){

        PageForm pageForm = new PageForm(pageNum,pageSize);

        if("".equals(goodName)){
            goodName=null;
        }

        PageInfo<BarterGoodsInfo> map = goodsInfoService.queryByAll(pageForm,classifyId,goodName,collectionType,priceType,timeType,viewCountType);

        return ResultInfoUtil.buildSuccess(map);
    }

    /**
     * 查询用户拥有的商品
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @param userId userId
     * @param type type
     * @return ResultInfo
     */
    @ApiOperation("用户拥有的商品")
    @GetMapping("/user")
    public ResultInfo queryGoodsByUserId(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize,
                                         @RequestParam("userId") long userId,
                                         @RequestParam("type") Integer type){
        PageForm pageForm = new PageForm(pageNum,pageSize);
        PageInfo<BarterGoodsInfo> barterGoodsInfoPageInfo = goodsInfoService.queryGoodsByUserId(pageForm,userId,type);
        return ResultInfoUtil.buildSuccess(barterGoodsInfoPageInfo);
    }


    /**
     * 普通用户获取指定id商品信息
     * @param id id
     * @return ResultInfo
     */
    @ApiOperation("普通用户获取指定id商品信息")
    @GetMapping("/id/{id}")
    public ResultInfo queryGoodById(
            @PathVariable(name = "id") Long id
    ){

        //判断id合法性
        if(id<=0){
            return ResultInfoUtil.buildError("请输入正确的id");
        }
        /*
        获取结果集
         */
        BarterGoodsInfo barterGoodsInfo = goodsInfoService.queryGoodById(id);
        /*
        判断返回结果
         */
        if(barterGoodsInfo == null){
            return ResultInfoUtil.buildError("非法访问,该商品不存在");
        }
        if(barterGoodsInfo.getGoodId() == -2){
            return ResultInfoUtil.buildSuccess("该商品正在审核中或库存不足");
        }

        return ResultInfoUtil.buildSuccess(barterGoodsInfo);
    }
}