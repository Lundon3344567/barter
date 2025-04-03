package com.example.barter_good.controller;


import com.example.barter_good.common.util.ResultInfoUtil;
import com.example.barter_good.domain.entity.BarterGoodsInfo;
import com.example.barter_good.domain.form.GoodsForm;
import com.example.barter_good.domain.form.InsertGoodsForm;
import com.example.barter_good.domain.form.PageForm;
import com.example.barter_good.lang.ResultInfo;
import com.example.barter_good.service.GoodsInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ChengKe
 * @description 商品列表请求控制层
 * @create 2021/11/8 21:45
 */
@RestController
@RequestMapping("/admin/goods")
@Api(tags = "管理员商品管理相关")
public class AdminGoodsInfoController {

    @Autowired
    private GoodsInfoService goodsInfoService;

    /**
     * 分页查询商品
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return ResultInfo
     */
    @ApiOperation("分页查询商品接口")
    @GetMapping("/page")
    public ResultInfo queryAllGoods(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize
    ){
        PageForm pageForm = new PageForm(pageNum, pageSize);


        PageInfo<BarterGoodsInfo> map = goodsInfoService.queryGoodsByPage(pageForm);

        return ResultInfoUtil.buildSuccess(map);
    }

    /**
     * 获取指定id商品信息
     * @param id id
     * @return ResultInfo
     */
    @ApiOperation("获取指定id商品信息")
    @GetMapping("/id/{id}")
    public ResultInfo getlistById(
            @PathVariable(name = "id") Long id
        ){

        //判断id合法性
        if(id<=0){
            return ResultInfoUtil.buildError("请输入正确的id");
        }
        /*
        获取结果集
         */
        BarterGoodsInfo list = goodsInfoService.getListById(id);
        /*
        判断返回结果
         */
        if(list == null){
            return ResultInfoUtil.buildError("非法访问");
        }
        return ResultInfoUtil.buildSuccess(list);
    }

    /**
     * 插入商品信息
     * @param insertGoodsForm insertGoodsForm
     * @return ResultInfo
     */
//    @PreAuthorize("hasAuthority('admin:good:save')")
    @ApiOperation("插入商品信息")
    @PostMapping()
//    @DoLog(operateModule = "GOODS", operateType = OperateTypeContains.OPERATE_INSERT, operateDesc = "插入商品信息")
    public ResultInfo adGoods(@Valid @RequestBody InsertGoodsForm insertGoodsForm){

        if (goodsInfoService.adGoods(insertGoodsForm) == 1){
            return ResultInfoUtil.buildSuccess("商品注册成功");
        }else{
            return ResultInfoUtil.buildError("商品注册失败");
        }
    }

    /**
     * 更新商品信息
     * @param barterGoodsInfo f
     * @return 1
     */
//    @PreAuthorize("hasAuthority('admin:good:update')")
    @ApiOperation("更新商品信息")
    @PutMapping()
//    @DoLog(operateModule = "GOODS", operateType = OperateTypeContains.OPERATE_UPDATE, operateDesc = "更新商品信息")
    public ResultInfo updateGoods(@RequestBody BarterGoodsInfo barterGoodsInfo){

        if (goodsInfoService.updateGoods(barterGoodsInfo) == 1){
            return ResultInfoUtil.buildSuccess("商品信息更新成功");
        }else{
            return ResultInfoUtil.buildError("商品信息更新失败");
        }
    }

    /**
     * 删除商品信息
     * @param id id
     * @return ResultInfo
     */
//    @PreAuthorize("hasAuthority('admin:good:delete')")
    @ApiOperation("删除商品信息")
    @DeleteMapping("/{id}")
//    @DoLog(operateModule = "GOODS", operateType = OperateTypeContains.OPERATE_DELETE, operateDesc = "删除商品信息", requestStyle = LogConstants.REQUEST_COMMON_STYLE)
    public ResultInfo deleteGoods(
            @PathVariable(name = "id") Long id){

        //判断id合法性
        if(id<=0){
            return ResultInfoUtil.buildError("请输入正确的id");
        }

        if (goodsInfoService.deleteGoods(id) == 1){
            return ResultInfoUtil.buildSuccess("删除商品成功");
        }else{
            return ResultInfoUtil.buildError("删除商品失败");
        }
    }

    /**
     * 模糊查询商品
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

        PageInfo<BarterGoodsInfo> map = goodsInfoService.adQueryByName(pageForm,goodsForm.getGoodName());

        return ResultInfoUtil.buildSuccess(map);
    }

    /**
     * 按创建时间排序商品(查看最新发布商品)
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

        PageInfo<BarterGoodsInfo> map = goodsInfoService.queryByClassification(pageForm,id);

        return ResultInfoUtil.buildSuccess(map);
    }

}