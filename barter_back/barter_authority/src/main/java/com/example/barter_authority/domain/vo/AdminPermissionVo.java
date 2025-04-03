package com.example.barter_authority.domain.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author walnut
 * @version 1.0
 * @className adminMenuDto
 * @description 管理员菜单列表的dto
 * @date 2021/11/21 3:33 下午
 */
public class AdminPermissionVo {

    private Long id;

    private String name;

    private String title;

    private String icon;

    private String path;

    private String component;

    private String perms;

    private List<AdminPermissionVo> children = new ArrayList<>();

    public AdminPermissionVo(Long id, String name, String title, String icon, String path, String component, String perms, List<AdminPermissionVo> children) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.icon = icon;
        this.path = path;
        this.component = component;
        this.perms = perms;
        this.children = children;
    }

    public AdminPermissionVo() {
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public List<AdminPermissionVo> getChildren() {
        return children;
    }

    public void setChildren(List<AdminPermissionVo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "AdminPermissionVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", path='" + path + '\'' +
                ", component='" + component + '\'' +
                ", perms='" + perms + '\'' +
                ", children=" + children +
                '}';
    }
}

