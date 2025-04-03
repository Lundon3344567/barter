package com.example.barter_authority.domain.entity;

import java.io.Serializable;

/**
 * (BarterRolePermission)实体类
 *
 * @author makejava
 * @since 2021-11-09 13:35:33
 */
public class BarterRolePermission implements Serializable {
    private static final long serialVersionUID = -35654993220510026L;
    
    private Integer id;
    
    private String roleId;
    
    private String permissionId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public BarterRolePermission(Integer id, String roleId, String permissionId) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public BarterRolePermission() {
    }
}

