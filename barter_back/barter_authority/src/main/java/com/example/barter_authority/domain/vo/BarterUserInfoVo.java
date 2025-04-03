package com.example.barter_authority.domain.vo;

/**
 * @Author walnut
 * @Date 2021/11/5 3:52 下午
 * @Version 1.0
 */
public class BarterUserInfoVo {
    private Long userId;

    private String username;

    private Integer userState;


    public BarterUserInfoVo(Long userId, String username, int userState) {
        this.userId = userId;
        this.username = username;
        this.userState = userState;
    }

    public BarterUserInfoVo() {
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

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }
}
