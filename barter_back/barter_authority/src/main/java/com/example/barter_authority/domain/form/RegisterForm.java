package com.example.barter_authority.domain.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author walnut
 * @version 1.0
 * @className RegisterForm
 * @description 注册用户的表单
 * @date 2021/11/16 1:23 下午
 */
public class RegisterForm {

    @Email
    private String email;

    @NotBlank
    private String username;

    private String password;

    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RegisterForm(String email, String username, String password, String code) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.code = code;
    }

    public RegisterForm() {
    }

    @Override
    public String toString() {
        return "RegisterForm{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

