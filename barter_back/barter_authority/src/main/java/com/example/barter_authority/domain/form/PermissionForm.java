package com.example.barter_authority.domain.form;

/**
 * @author walnut
 * @version 1.0
 * @className PermissionForm
 * @description 接收前端要添加或者修改的Permission数据
 * @date 2021/11/14 4:18 下午
 */
public class PermissionForm {

    private Integer id;

    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     * 0：目录
     * 1：菜单
     * 2：按钮
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

    private Integer statu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    @Override
    public String toString() {
        return "PermissionForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", path='" + path + '\'' +
                ", perms='" + perms + '\'' +
                ", component='" + component + '\'' +
                ", statu=" + statu +
                '}';
    }
}

