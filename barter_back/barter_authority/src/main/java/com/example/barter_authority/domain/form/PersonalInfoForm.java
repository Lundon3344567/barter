package com.example.barter_authority.domain.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author walnut
 * @version 1.0
 * @className UserForm
 * @description 角色表单，用户收取前端传来的数据
 * @date 2021/11/11 2:05 下午
 */
public class PersonalInfoForm {

    private static final long serialVersionUID = 161685358680097407L;

//    @NotBlank
    @Min(1)
    private Integer userId;

    @NotBlank
    private String userSid;

    private String nickname;

    @Max(1)
    @Min(0)
    private Integer gender;

    @Past
    private Date birth;

    @Max(1)
    @Min(0)
    private Integer campus;

    @Email
    private String stuEmail;

    @Pattern(regexp = "^1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}$")
    private String userPhone;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserSid() {
        return userSid;
    }

    public void setUserSid(String userSid) {
        this.userSid = userSid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Integer getCampus() {
        return campus;
    }

    public void setCampus(Integer campus) {
        this.campus = campus;
    }

    public String getStuEmail() {
        return stuEmail;
    }

    public void setStuEmail(String stuEmail) {
        this.stuEmail = stuEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PersonalInfoForm{" +
                "userId=" + userId +
                ", userSid='" + userSid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender=" + gender +
                ", birth=" + birth +
                ", campus=" + campus +
                ", stuEmail='" + stuEmail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}

