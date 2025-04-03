package com.example.barter_good.domain.form;

import java.io.Serializable;

/**
 * @ClassName GoodsReleasedFrom
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-12-05 21:09
 * @Version 1.0
 **/
public class GoodsReleasedFrom implements Serializable {

    private Long ownerId;

    private String goodName;

    private Double goodPrice;

    private String imgUrl;

    private String goodDescribe;

    private Long classifiedNum;

    private Integer goodNum;

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

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    @Override
    public String toString() {
        return "GoodsReleasedFrom{" +
                "ownerId=" + ownerId +
                ", goodName='" + goodName + '\'' +
                ", goodPrice=" + goodPrice +
                ", imgUrl='" + imgUrl + '\'' +
                ", goodDescribe='" + goodDescribe + '\'' +
                ", classifiedNum=" + classifiedNum +
                ", goodNum=" + goodNum +
                '}';
    }
}
