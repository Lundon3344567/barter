package com.example.barter_authority.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (BarterPermissionInfo)实体类
 *
 * @author makejava
 * @since 2021-11-12 13:42:04
 */
public class BarterPermissionInfo implements Serializable {
    private static final long serialVersionUID = 410300300854157428L;
    
    private Long id;
    
    private String name;
    /**
     * 类型     0：目录   1：菜单   2：按钮
     */
    private Integer type;
    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;
    /**
     * 菜单URL
     */
    private String path;
    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;
    
    private String component;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer ordernum;
    
    private Date created;
    
    private Date updated;
    
    private Integer statu;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    @Override
    public String toString() {
        return "BarterPermissionInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", path='" + path + '\'' +
                ", perms='" + perms + '\'' +
                ", component='" + component + '\'' +
                ", icon='" + icon + '\'' +
                ", ordernum=" + ordernum +
                ", created=" + created +
                ", updated=" + updated +
                ", statu=" + statu +
                '}';
    }
}

