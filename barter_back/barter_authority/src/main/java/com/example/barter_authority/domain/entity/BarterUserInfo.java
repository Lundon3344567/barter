package com.example.barter_authority.domain.entity;

import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * (BarterUserInfo)实体类
 *
 * @author makejava
 * @since 2021-11-09 13:34:30
 */
@ToString
public class BarterUserInfo implements Serializable {
    private static final long serialVersionUID = 161685358680097407L;
    
    private Long userId;
    
    private String username;
    
    private String userSid;
    
    private String password;
    
    private String email;
    
    private String nickname;
    
    private Integer gender;
    
    private Date birth;
    
    private Integer campus;
    
    private Date createTime;
    
    private Date lastEditTime;
    
    private Integer credit;
    
    private String stuEmail;
    
    private String userPhone;
    
    private Integer userState;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserSid() {
        return userSid;
    }

    public void setUserSid(String userSid) {
        this.userSid = userSid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
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

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }


    @Override
    public String toString() {
        return "BarterUserInfo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", userSid='" + userSid + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender=" + gender +
                ", birth=" + birth +
                ", campus=" + campus +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", credit=" + credit +
                ", stuEmail='" + stuEmail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userState=" + userState +
                '}';
    }
}

