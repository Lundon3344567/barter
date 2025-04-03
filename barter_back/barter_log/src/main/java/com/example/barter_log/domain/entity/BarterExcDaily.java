package com.example.barter_log.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (BarterExcDaily)实体类
 *
 * @author makejava
 * @since 2021-11-22 16:22:08
 */
public class BarterExcDaily implements Serializable {
    private static final long serialVersionUID = 627459692721411325L;
    /**
     * 主键ID
     */
    private String excId;
    /**
     * 请求参数
     */
    private String excReqParam;
    /**
     * 异常名
     */
    private String excName;
    /**
     * 异常信息
     */
    private String excMes;
    /**
     * 操作者ID
     */
    private String operateUserId;
    /**
     * 操作者名称
     */
    private String operateUserName;
    /**
     * 操作方法
     */
    private String operateMethod;
    /**
     * 请求URI
     */
    private String operateUri;
    /**
     * 请求IP
     */
    private String operateIp;
    /**
     * 操作时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date operateTime;
    /**
     * 操作版本
     */
    private String operateVer;


    public String getExcId() {
        return excId;
    }

    public void setExcId(String excId) {
        this.excId = excId;
    }

    public String getExcReqParam() {
        return excReqParam;
    }

    public void setExcReqParam(String excReqParam) {
        this.excReqParam = excReqParam;
    }

    public String getExcName() {
        return excName;
    }

    public void setExcName(String excName) {
        this.excName = excName;
    }

    public String getExcMes() {
        return excMes;
    }

    public void setExcMes(String excMes) {
        this.excMes = excMes;
    }

    public String getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(String operateUserId) {
        this.operateUserId = operateUserId;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public String getOperateMethod() {
        return operateMethod;
    }

    public void setOperateMethod(String operateMethod) {
        this.operateMethod = operateMethod;
    }

    public String getOperateUri() {
        return operateUri;
    }

    public void setOperateUri(String operateUri) {
        this.operateUri = operateUri;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateVer() {
        return operateVer;
    }

    public void setOperateVer(String operateVer) {
        this.operateVer = operateVer;
    }

    @Override
    public String toString() {
        return "BarterExcDaily{" +
                "excId='" + excId + '\'' +
                ", excReqParam='" + excReqParam + '\'' +
                ", excName='" + excName + '\'' +
                ", excMes='" + excMes + '\'' +
                ", operateUserId='" + operateUserId + '\'' +
                ", operateUserName='" + operateUserName + '\'' +
                ", operateMethod='" + operateMethod + '\'' +
                ", operateUri='" + operateUri + '\'' +
                ", operateIp='" + operateIp + '\'' +
                ", operateTime=" + operateTime +
                ", operateVer='" + operateVer + '\'' +
                '}';
    }
}

