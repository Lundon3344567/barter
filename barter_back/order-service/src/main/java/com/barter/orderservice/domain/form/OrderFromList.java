package com.barter.orderservice.domain.form;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName OrderFromList
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-12-04 23:41
 * @Version 1.0
 **/
public class OrderFromList implements Serializable {
    private List<OrderFrom> orderFromList;

    public List<OrderFrom> getOrderFromList() {
        return orderFromList;
    }

    public void setOrderFromList(List<OrderFrom> orderFromList) {
        this.orderFromList = orderFromList;
    }

    public OrderFromList() {
        super();
    }

    public OrderFromList(List<OrderFrom> orderFromList) {
        this.orderFromList = orderFromList;
    }
}
