package com.example.barter_good.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * (BarterGoodsClassify)实体类
 *
 * @author makejava
 * @since 2021-11-09 16:05:45
 */
public class BarterGoodsClassify implements Serializable {
    private static final long serialVersionUID = -35187911676538268L;
    
    private Long classifyId;
    
    private String name;
    
    private Long parentId;
    
    private Long sonId;

    private List<BarterGoodsClassify> children = new ArrayList<>();

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getSonId() {
        return sonId;
    }

    public void setSonId(Long sonId) {
        this.sonId = sonId;
    }

    public List<BarterGoodsClassify> getChildren() {
        return children;
    }

    public void setChildren(List<BarterGoodsClassify> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "BarterGoodsClassify{" +
                "classifyId=" + classifyId +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", sonId=" + sonId +
                ", children=" + children +
                '}';
    }
}

