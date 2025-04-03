package com.example.barter_good.domain.form;

/**
 * @author chengke
 * @version 1.0
 * @className GoodsForm
 * @description 模糊查询参数封装类
 * @date 2021/11/17 18:41
 */
public class GoodsForm {

    private int pageNum;

    private int pageSize;

    private String goodName;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GoodsForm{");
        sb.append("pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", goodName='").append(goodName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}