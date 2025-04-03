package com.example.barter_log.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (BarterOperateDaily)实体类
 *
 * @author makejava
 * @since 2021-11-22 16:21:44
 */
public class BarterOperateDaily implements Serializable {
    private static final long serialVersionUID = 273155850236270705L;
    /**
     * 主键ID
     */
    private String operateId;
    /**
     * 操作模块
     */
    private String operateModul;
    /**
     * 操作类型
     */
    private String operateType;
    /**
     * 操作详情
     */
    private String operateDesc;
    /**
     * 请求参数
     */
    private String operateReqParam;
    /**
     * 响应参数
     */
    private String operateResParam;
    /**
     * 操作者ID
     */
    private String operateUserId;
    /**
     * 操作者名字
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
     * 版本号
     */
    private String operateVer;


    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public String getOperateModul() {
        return operateModul;
    }

    public void setOperateModul(String operateModul) {
        this.operateModul = operateModul;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateDesc() {
        return operateDesc;
    }

    public void setOperateDesc(String operateDesc) {
        this.operateDesc = operateDesc;
    }

    public String getOperateReqParam() {
        return operateReqParam;
    }

    public void setOperateReqParam(String operateReqParam) {
        this.operateReqParam = operateReqParam;
    }

    public String getOperateResParam() {
        return operateResParam;
    }

    public void setOperateResParam(String operateResParam) {
        this.operateResParam = operateResParam;
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

}

