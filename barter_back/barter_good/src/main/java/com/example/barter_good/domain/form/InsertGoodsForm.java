package com.example.barter_good.domain.form;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * @author chengke
 * @version 1.0
 * @className InsertGoodsForm
 * @description 插入商品信息使用类
 * @date 2021/11/20 18:45
 */
public class InsertGoodsForm {

    //商品拥有者id

    @DecimalMin("0")
    private Long ownerId;

    //商品名称

    @NotNull
    private String goodName;

    //商品价格

    @DecimalMin("0")
    private Double goodPrice;

    //商品图片

    @NotNull
    private String imgUrl;

    //商品描述

    private String goodDescribe;

    //分类id

    @DecimalMin("0")
    private Long classifiedNum;

    //商品库存数量

    @DecimalMin("1")
    private Integer goodNum;

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(Double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGoodDescribe() {
        return goodDescribe;
    }

    public void setGoodDescribe(String goodDescribe) {
        this.goodDescribe = goodDescribe;
    }

    public Long getClassifiedNum() {
        return classifiedNum;
    }

    public void setClassifiedNum(Long classifiedNum) {
        this.classifiedNum = classifiedNum;
    }
}