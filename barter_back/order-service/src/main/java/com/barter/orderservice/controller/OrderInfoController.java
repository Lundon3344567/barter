package com.barter.orderservice.controller;

import com.barter.orderservice.common.annotation.AccessLimit;
import com.barter.orderservice.common.annotation.Limit;
import com.barter.orderservice.common.annotation.NoRepeatSubmit;
import com.barter.orderservice.common.lang.ResultInfo;
import com.barter.orderservice.common.util.ResultInfoUtil;
import com.barter.orderservice.domain.form.OrderFrom;
import com.barter.orderservice.domain.form.OrderFromList;
import com.barter.orderservice.domain.form.PageForm;
import com.barter.orderservice.mapper.BarterGoodsInfoMapper;
import com.barter.orderservice.service.OrderInfoService;
//import com.barter.orderservice.service.WsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName OrderInfoController
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-30 16:18
 * @Version 1.0
 **/
@RestController
@Api(tags = "订单")
@RequestMapping("/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

//    @Autowired
//    private WsService wsService;

    @Autowired
    private BarterGoodsInfoMapper barterGoodsInfoMapper;

    @NoRepeatSubmit
    @Limit(key = "limit", permitsPerSecond = 200, timeout = 500, timeunit = TimeUnit.MILLISECONDS,msg = "当前排队人数较多，请稍后再试！")
    @ApiOperation("添加订单")
    @PostMapping()
    public ResultInfo orderAdd(@RequestBody @Validated OrderFromList orderFromList){

        if(orderInfoService.addOrder(orderFromList.getOrderFromList()) > 0){
            return ResultInfoUtil.buildSuccess("添加订单成功");
        }else{
            return ResultInfoUtil.buildError("添加订单失败");
        }
    }

    @ApiOperation("取消订单")
    @PutMapping()
    public ResultInfo orderModify(@RequestBody OrderFrom orderFrom){
        if(orderInfoService.cancelOrder(orderFrom.getId()) > 0){
            return ResultInfoUtil.buildSuccess("取消订单成功");
        }else {
            return ResultInfoUtil.buildError("取消订单失败");
        }
    }

    @ApiOperation("分页查询用户订单")
    @GetMapping()
    public ResultInfo queryOrderByUserId(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("userId") long userId
    ){
        PageForm pageForm  = new PageForm(pageNum,pageSize);
        return ResultInfoUtil.buildSuccess(orderInfoService.queryOrderByUserId(pageForm,userId));
    }

    @ApiOperation("分页查询所有订单")
    @GetMapping("/all")
    @AccessLimit(limit = 2,sec = 5)
    public ResultInfo queryOrderAll(
            HttpServletRequest request,
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize
    ){
        PageForm pageForm = new PageForm(pageNum,pageSize);
        return ResultInfoUtil.buildSuccess(orderInfoService.queryOrderAll(pageForm));
    }

    @ApiOperation("用户根据状态查询订单")
    @GetMapping("/user")
    public ResultInfo queryOrderFlagByUserId(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("userId") long userId,
            @RequestParam("flag") int flag
    ){
        PageForm pageForm = new PageForm(pageNum,pageSize);
        return ResultInfoUtil.buildSuccess(orderInfoService.queryOrderFlagByUserId(pageForm,userId,flag));
    }

    @ApiOperation("查询订单过期时间")
    @GetMapping("/key")
    public ResultInfo queryOrderAll(@RequestParam("id") long id){
        return ResultInfoUtil.buildSuccess(orderInfoService.expirationTimeByKey(id));
    }

//    @ApiOperation("统计订单")
//    @GetMapping("/id")
//    public ResultInfo countOrderById(){
//        return ResultInfoUtil.buildSuccess(orderInfoService.countOrderById());
//    }

    @ApiOperation("支付订单")
    @PutMapping("/id")
    public ResultInfo orderPay(@RequestBody OrderFrom orderFrom){
        if(orderInfoService.paymentOrder(orderFrom.getId()) > 0){
            return ResultInfoUtil.buildSuccess("支付订单成功");
        }else {
            return ResultInfoUtil.buildError("支付订单失败,请确认订单状态是存在且未支付的");
        }
    }



}
