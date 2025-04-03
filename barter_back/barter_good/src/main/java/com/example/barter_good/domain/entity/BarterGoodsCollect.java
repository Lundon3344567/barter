package com.example.barter_good.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (BarterGoodsCollect)实体类
 *
 * @author makejava
 * @since 2021-11-09 16:05:45
 */
public class BarterGoodsCollect implements Serializable {
    private static final long serialVersionUID = 889195931726667948L;
    
    private long collectId;
    
    private long goodsId;
    
    private long userId;
    
    private Date createdTime;
    
    private Integer state;


    public long getCollectId() {
        return collectId;
    }

    public void setCollectId(long collectId) {
        this.collectId = collectId;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}

