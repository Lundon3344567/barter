package com.barter.orderservice.domain.form;

/**
 * @author walnut
 * @version 1.0
 * @date 2021/11/9 10:43 下午
 */
public class PageForm {

    /**
     * 请求页
     */
    private int requestPage;
    /**
     * 每页显示多少条
     */
    private int pageSize;


    public PageForm(int requestPage, int pageSize) {
        this.requestPage = requestPage;
        this.pageSize = pageSize;
    }

    public PageForm() {
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
}

