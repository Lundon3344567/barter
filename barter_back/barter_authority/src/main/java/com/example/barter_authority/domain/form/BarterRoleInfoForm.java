package com.example.barter_authority.domain.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * @author walnut
 * @version 1.0
 * @className BarterRoleInfoForm
 * @description TODO
 * @date 2021/12/11 13:46
 */
public class BarterRoleInfoForm {
    private static final long serialVersionUID = -33470201037373775L;

    @Min(1)
    private Long id;

    @NotBlank
    private String name;

    private String available;

    private String Code;

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
}
