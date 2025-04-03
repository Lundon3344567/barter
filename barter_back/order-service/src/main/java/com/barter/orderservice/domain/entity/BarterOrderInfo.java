package com.barter.orderservice.domain.entity;

//import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * (BarterOrderInfo)实体类
 *
 * @author makejava
 * @since 2021-11-28 23:35:30
 */
public class BarterOrderInfo implements Serializable {
    private static final long serialVersionUID = 610832825825980019L;
    /**
     * 订单id
     */
    @NotBlank(message = "订单id不能为空且不能为负数")
    @Min(0)
    private Long id;
    /**
     * 卖家id
     */
    @NotBlank(message = "卖家id不能为空且不能为负数")
    @Min(0)
    private Long sellerId;
    /**
     * 买家id
     */
    @NotBlank(message = "买家id不能为空且不能为负数")
    @Min(0)
    private Long buyerId;
    /**
     * 商品id
     */
    @NotBlank(message = "商品id不能为空且不能为负数")
    @Min(0)
    private Long goodId;
    /**
     * 商品购买数量
     */
    @NotBlank(message = "商品购买数量不能为空且不能为负数")
    @Min(0)
    private Integer goodNum;
    /**
     * 订单金额
     */
    @NotBlank(message = "订单金额不能为空且不能为负数")
    @Min(0)
    private Double orderMoney;
    /**
     * 实付金额
     */
    @NotBlank(message = "实付金额不能为空且不能为负数")
    @Min(0)
    private Double payMoney;
    /**
     * 付款时间
     */
    private Date payTime;
    /**
     * 订单状态
     */
    private Integer orderFlag;
    /**
     * 订单创建时间
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

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
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

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
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

