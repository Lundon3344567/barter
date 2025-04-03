package com.example.barter_authority.domain.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author walnut
 * @version 1.0
 * @className adminMenuDto
 * @description 用户权限的dto类
 * @date 2021/11/21 3:33 下午
 */
public class AdminPermissionDto {

    private Long id;

    private String name;

    private String title;

    private String icon;

    private String path;

    private String component;

    private Long parentId;
    /**
     * 菜单URL
     */

    private List<AdminPermissionDto> children = new ArrayList<>();

    public AdminPermissionDto() {
    }

    public AdminPermissionDto(Long id, String name, String title, String icon, String path, String component, Long parentId, List<AdminPermissionDto> children) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.icon = icon;
        this.path = path;
        this.component = component;
        this.parentId = parentId;
        this.children = children;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<AdminPermissionDto> getChildren() {
        return children;
    }

    public void setChildren(List<AdminPermissionDto> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "AdminPermissionDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", path='" + path + '\'' +
                ", component='" + component + '\'' +
                ", parentId=" + parentId +
                ", children=" + children +
                '}';
    }
}

