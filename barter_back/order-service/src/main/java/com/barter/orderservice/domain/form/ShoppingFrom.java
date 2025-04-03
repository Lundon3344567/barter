package com.barter.orderservice.domain.form;

/**
 * @ClassName ShoppingFrom
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-29 22:42
 * @Version 1.0
 **/
public class ShoppingFrom {

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

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }
}
