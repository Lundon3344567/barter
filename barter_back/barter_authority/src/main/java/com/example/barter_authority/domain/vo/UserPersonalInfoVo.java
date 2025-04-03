package com.example.barter_authority.domain.vo;

/**
 * @ClassName UserPersonalInfoVo
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-24 16:24
 * @Version 1.0
 **/
public class UserPersonalInfoVo {
    private String username;

    private String nickname;

    private String email;

    private Integer campus;

    private Integer credit;

    private String userPhone;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "UserPersonalInfoVo{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", campus=" + campus +
                ", credit=" + credit +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}
