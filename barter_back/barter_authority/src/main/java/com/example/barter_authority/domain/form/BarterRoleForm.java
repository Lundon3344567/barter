package com.example.barter_authority.domain.form;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author walnut
 * @version 1.0
 * @className BarterRoleForm
 * @description TODO
 * @date 2021/12/14 19:02
 */
public class BarterRoleForm {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String available;

    @NotBlank
    private String code;

    @Override
    public String toString() {
        return "BarterRoleForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", available='" + available + '\'' +
                ", code='" + code + '\'' +
                '}';
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
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
