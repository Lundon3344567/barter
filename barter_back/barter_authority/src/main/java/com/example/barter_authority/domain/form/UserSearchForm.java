package com.example.barter_authority.domain.form;

import javax.validation.constraints.DecimalMin;

public class UserSearchForm {

    private String name;

    private String tel;

    private Integer state;

    @DecimalMin("1")
    private int requestPage;

    @DecimalMin("1")
    private int pageSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getRequestPage() {
        return requestPage;
    }

    public void setRequestPage(int requestPage) {
        this.requestPage = requestPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserSearchForm{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", state=" + state +
                ", requestPage=" + requestPage +
                ", pageSize=" + pageSize +
                '}';
    }
}
