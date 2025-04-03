package com.example.barter_authority.domain.form;

import java.util.List;

/**
 * @author walnut
 * @version 1.0
 * @className RoleUpdateForm
 * @description TODO
 * @date 2021/12/14 19:07
 */
public class RoleUpdateForm {

    private List<Integer> keys;
    private Integer roleId;

    public List<Integer> getKeys() {
        return keys;
    }

    public void setKeys(List<Integer> keys) {
        this.keys = keys;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "RoleUpdateForm{" +
                "keys=" + keys +
                ", roleId=" + roleId +
                '}';
    }
}
