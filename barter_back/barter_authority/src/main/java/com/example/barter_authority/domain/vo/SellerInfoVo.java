package com.example.barter_authority.domain.vo;

/**
 * @author walnut
 * @version 1.0
 * @className SellerInfoVo
 * @description TODO
 * @date 2021/11/23 7:11 下午
 */
public class SellerInfoVo {
    private Long userId;

    private String username;

    private String email;

    private String nickname;

    private Integer gender;

    private Integer campus;

    private Integer credit;

    private String userPhone;

    private Integer userState;


    public SellerInfoVo(Long userId, String username, String email, String nickname, Integer gender, Integer campus, Integer credit, String userPhone, Integer userState) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.campus = campus;
        this.credit = credit;
        this.userPhone = userPhone;
        this.userState = userState;
    }

    public SellerInfoVo() {
    }

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

    public Integer getCampus() {
        return campus;
    }

    public void setCampus(Integer campus) {
        this.campus = campus;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
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
        return "SellerInfoVo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender=" + gender +
                ", campus=" + campus +
                ", credit=" + credit +
                ", userPhone='" + userPhone + '\'' +
                ", userState=" + userState +
                '}';
    }
}

