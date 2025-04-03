package com.barter.orderservice.domain.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @ClassName OrderFrom
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-29 21:00
 * @Version 1.0
 **/
public class OrderFrom implements Serializable {

    /**
     * 订单id
     */
    @NotBlank(message = "请检查参数，订单id不能为空且不能为负数")
    @Min(0)
    private Long id;

    /**
     * 买家id
     */

    private Long buyerId;
    /**
     * 商品id
     */
    private Long goodId;
    /**
     * 商品购买数量
     */

    private Integer goodNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
