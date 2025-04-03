package com.barter.orderservice.service;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName goodsClient
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-12-30 20:51
 * @Version 1.0
 **/
@FeignClient("barter-good-service")
public interface goodsClient {

    /**
     * 查询商品库存数量
     * @param id 商品id
     * @return 是否有库存
     */
    @GetMapping("/ordergoods/num")
    Boolean queryGoodsNum(@RequestParam("id") Long id);


    @PutMapping("/ordergoods")
    Boolean increaseGoodsNum(@RequestParam("id") Long goodsId, @RequestParam("num") Integer num, @RequestParam("type") Integer type);

}
