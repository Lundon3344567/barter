package com.example.barter_authority.domain.entity;

import java.io.Serializable;

/**
 * (BarterRoleInfo)实体类
 *
 * @author makejava
 * @since 2021-11-13 00:19:17
 */
public class BarterRoleInfo implements Serializable {
    private static final long serialVersionUID = -33470201037373775L;
    
    private Long id;
    
    private String name;
    
    private String available;
    
    private String Code;


    public BarterRoleInfo(Long id, String name, String available, String code) {
        this.id = id;
        this.name = name;
        this.available = available;
        Code = code;
    }

    public BarterRoleInfo() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    @Override
    public String toString() {
        return "BarterRoleInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", available='" + available + '\'' +
                ", Code='" + Code + '\'' +
                '}';
    }
}

