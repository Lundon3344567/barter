package com.barter.orderservice.domain.vo;

import java.util.Date;

/**
 * @author chengke
 * @version 1.0
 * @className ShoppingCartVo
 * @description 查询购物车返回信息
 * @date 2021/11/30 9:38
 */
public class ShoppingCartVo {

    /**
     * 购物车id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商品id
     */
    private Long goodId;
    /**
     * 商品名称
     */
    private String goodName;
    /**
     * 商品用有人id
     */
    private Long goodOwnerId;
    /**
     * 商品拥有人姓名
     */
    private String goodOwnerName;
    /**
     * 商品价格
     */
    private Double goodPrice;
    /**
     * 商品图片
     */
    private String imgUrl;
    /**
     * 商品购买数量
     */
    private Integer goodNum;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最近修改时间
     */
    private Date lastEditTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Long getGoodOwnerId() {
        return goodOwnerId;
    }

    public void setGoodOwnerId(Long goodOwnerId) {
        this.goodOwnerId = goodOwnerId;
    }

    public String getGoodOwnerName() {
        return goodOwnerName;
    }

    public void setGoodOwnerName(String goodOwnerName) {
        this.goodOwnerName = goodOwnerName;
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

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}