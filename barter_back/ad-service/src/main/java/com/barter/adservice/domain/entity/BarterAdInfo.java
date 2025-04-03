package com.barter.adservice.domain.entity;


//import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * (BarterAdInfo)实体类
 *
 * @author makejava
 * @since 2021-11-09 16:05:45
 */
public class BarterAdInfo implements Serializable {
    //private static final long serialVersionUID = -89028504494767634L;

    private Long adId;

    @NotBlank(message = "广告名不能为空")
    private String adName;
    
    private Date createTime;
    
    private Date lastEditTime;
    
    private Date verifyTime;

    @NotBlank(message = "图片不能为空")
    private String imgUrl;

    @NotBlank(message = "广告描述信息不能为空")
    private String adDescribe;

    @NotBlank(message = "广告链接不能为空")
    private String adUrl;
    
    private Long auditId;
    
    private Date adValidity;
    
    private Integer adState;


    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
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

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAdDescribe() {
        return adDescribe;
    }

    public void setAdDescribe(String adDescribe) {
        this.adDescribe = adDescribe;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public Date getAdValidity() {
        return adValidity;
    }

    public void setAdValidity(Date adValidity) {
        this.adValidity = adValidity;
    }

    public Integer getAdState() {
        return adState;
    }

    public void setAdState(Integer adState) {
        this.adState = adState;
    }

    @Override
    public String toString() {
        return "BarterAdInfo{" +
                "adId=" + adId +
                ", adName='" + adName + '\'' +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", verifyTime=" + verifyTime +
                ", imgUrl='" + imgUrl + '\'' +
                ", adDescribe='" + adDescribe + '\'' +
                ", adUrl='" + adUrl + '\'' +
                ", auditId=" + auditId +
                ", adValidity=" + adValidity +
                ", adState=" + adState +
                '}';
    }
}

