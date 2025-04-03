package com.barter.orderservice.domain.form;


/**
 * @author chengke
 * @version 1.0
 * @className InsertShoppingCartForm
 * @description 购物车插入参数
 * @date 2021/11/29 17:05
 */
public class InsertShoppingCartForm {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商品id
     */
    private Long goodId;
    /**
     * 商品购买数量
     */
    private Integer goodNum;

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
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
}