package com.example.barter_good.controller;


import com.example.barter_good.common.util.ResultInfoUtil;
import com.example.barter_good.domain.entity.BarterGoodsCollect;
import com.example.barter_good.lang.ResultInfo;
import com.example.barter_good.service.GoodsCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName GoodsCollectController
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-16 11:25
 * @Version 1.0
 **/
@RestController
@Api(tags = "商品收藏")
@RequestMapping("/good")
public class GoodsCollectController {

    @Autowired
    private GoodsCollectService goodsCollectService;

    @ApiOperation("商品收藏")
    @GetMapping("/collects")
    public ResultInfo goodsCollectAdd(@RequestParam("goodsId") long goodsId,
                                      @RequestParam("userId") long userId,
                                      @RequestParam("num") int num){
        BarterGoodsCollect barterGoodsCollect = new BarterGoodsCollect();
        barterGoodsCollect.setUserId(userId);
        barterGoodsCollect.setGoodsId(goodsId);
        if(num == 1){
            return ResultInfoUtil.buildSuccess(goodsCollectService.goodsCollectAdd(barterGoodsCollect));
        }else{
            return ResultInfoUtil.buildSuccess(goodsCollectService.goodsCollectDelete(goodsId,userId));
        }
    }

//    @ApiOperation("商品取消收藏")
//    @DeleteMapping("/collect/{id}")
//    public ResultInfo goodsCollectDelete(@PathVariable("id") int collectId){
//        if(goodsCollectService.goodsCollectDelete(collectId) > 0){
//            return ResultInfoUtil.buildSuccess("商品取消收藏成功");
//        }else{
//            return ResultInfoUtil.buildError("商品取消收藏失败");
//        }
//    }

//    @ApiOperation("商品被收藏数")
//    @GetMapping("/collect")
//    public ResultInfo goodsByGoodsid(@RequestParam("goodsId") int goodsId){
//        return ResultInfoUtil.buildSuccess(goodsCollectService.goodsByGoodid(goodsId));
//    }

    @ApiOperation("用户已收藏数")
    @GetMapping("/collect")
    public ResultInfo goodsByUserid(@RequestParam("userId") int userId){
        return ResultInfoUtil.buildSuccess(goodsCollectService.goodsByUserid(userId));
    }

    @ApiOperation("查找商品是否被用户收藏")
    @GetMapping("/ids")
    public ResultInfo collectByGoodsidUserid(
            @RequestParam("goodsId") long goodsId,
            @RequestParam("userId") long userId
    ){
        return ResultInfoUtil.buildSuccess(goodsCollectService.collectByGoodsidUserid(goodsId,userId));
    }


}
