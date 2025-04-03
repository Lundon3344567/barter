package com.example.barter_authority.domain.form;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

/**
 * @author walnut
 * @version 1.0
 * @className UserStateForm
 * @description TODO
 * @date 2021/12/10 11:22
 */
public class UserStateForm {

    @Min(1)
    private Integer userId;

    @Range(min = 1,max = 103)
    private Integer state;

    public UserStateForm(Integer userId, Integer state) {
        this.userId = userId;
        this.state = state;
    }

    public UserStateForm() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
