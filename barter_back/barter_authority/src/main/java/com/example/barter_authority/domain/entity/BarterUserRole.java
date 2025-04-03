package com.example.barter_authority.domain.entity;

import java.io.Serializable;

/**
 * (BarterUserRole)实体类
 *
 * @author makejava
 * @since 2021-11-09 13:34:30
 */
public class BarterUserRole implements Serializable {
    private static final long serialVersionUID = -32821700849068858L;
    
    private Long id;
    
    private Long userId;
    
    private Long roleId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public BarterUserRole(Long id, Long userId, Long roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public BarterUserRole() {
    }
}

