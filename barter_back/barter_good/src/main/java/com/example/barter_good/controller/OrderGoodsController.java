package com.example.barter_good.controller;

import com.example.barter_good.service.GoodsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chengke
 * @version 1.0
 * @className OrderGoodsController
 * @description 订单调用商品服务
 * @date 2021/12/30 11:06
 */
@RestController
@RequestMapping("/ordergoods")
@Api(tags = "订单调用商品服务相关")
public class OrderGoodsController {

    @Autowired
    private GoodsInfoService goodsInfoService;

    /**
     * 查询商品库存数量
     * @param id 商品id
     * @return 是否有库存
     */
    @ApiOperation("查询商品库存数量")
    @GetMapping("/num")
    public Boolean queryGoodsNum(
            @RequestParam("id") Long id){

        return goodsInfoService.queryGoodsNum(id);
    }

    /**
     * 商品加减库存
     * @param goodsId 商品id
     * @param num 数量
     * @return 是否操作成功
     */
    @ApiOperation("商品加减库存")
    @PutMapping()
    public Boolean increaseGoodsNum(
            @RequestParam("id") Long goodsId,
            @RequestParam("num") Integer num,
            @RequestParam("type") Integer type
    ){

        Boolean flag = false;
        if(type==1){
            flag = goodsInfoService.increaseGoodsNum(goodsId,num);
        }else if(type == 0){
            flag = goodsInfoService.decreaseGoodsNum(goodsId,num);
        }
        return flag;
    }

}