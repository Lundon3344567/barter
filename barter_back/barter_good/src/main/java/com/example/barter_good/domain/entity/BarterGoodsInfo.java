package com.example.barter_good.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (BarterGoodsInfo)实体类
 *
 * @author makejava
 * @since 2021-11-20 14:44:48
 */
public class BarterGoodsInfo implements Serializable {
    private static final long serialVersionUID = 232519662114651446L;

    /*
    商品id
     */

    private Long goodId;

    /*
    商品拥有者id
     */

    private Long ownerId;

    /*
    商品名称
     */

    private String goodName;

    /*
    商品价格
     */

    private Double goodPrice;

    /*
    商品标志
     */

    private Integer goodFlag;

    /*
    商品图片路径
     */

    private String imgUrl;

    /*
    商品描述
     */

    private String goodDescribe;

    /*
    商品收藏数
     */

    private Integer collectionNum;

    /*
    商品分类id
     */

    private Long classifiedNum;

    /*
    商品浏览量
     */

    private Integer viewCount;

    /*
    商品创建时间
     */

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;

    /*
    商品库存数量
     */

    private Integer goodNum;

    /*
    商家状态
     */
    private Integer ownerFlag;

    public Integer getOwnerFlag() {
        return ownerFlag;
    }

    public void setOwnerFlag(Integer ownerFlag) {
        this.ownerFlag = ownerFlag;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
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

    public Integer getGoodFlag() {
        return goodFlag;
    }

    public void setGoodFlag(Integer goodFlag) {
        this.goodFlag = goodFlag;
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

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }

    public Long getClassifiedNum() {
        return classifiedNum;
    }

    public void setClassifiedNum(Long classifiedNum) {
        this.classifiedNum = classifiedNum;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BarterGoodsInfo{" +
                "goodId=" + goodId +
                ", ownerId=" + ownerId +
                ", goodName='" + goodName + '\'' +
                ", goodPrice=" + goodPrice +
                ", goodFlag=" + goodFlag +
                ", imgUrl='" + imgUrl + '\'' +
                ", goodDescribe='" + goodDescribe + '\'' +
                ", collectionNum=" + collectionNum +
                ", classifiedNum=" + classifiedNum +
                ", viewCount=" + viewCount +
                ", createTime=" + createTime +
                ", goodNum=" + goodNum +
                '}';
    }
}

