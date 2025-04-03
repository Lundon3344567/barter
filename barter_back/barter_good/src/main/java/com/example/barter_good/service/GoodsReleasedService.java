package com.example.barter_good.service;

import com.example.barter_good.domain.entity.BarterGoodsClassify;
import com.example.barter_good.domain.entity.BarterGoodsInfo;
import com.example.barter_good.domain.form.GoodsReleasedFrom;
import com.example.barter_good.domain.form.PageForm;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName GoodsReleasedService
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-13 16:44
 * @Version 1.0
 **/
@Service
public interface GoodsReleasedService {

    /**
     * 根据父Id列出所有子分类
     * @param parentId
     * @return
     */
    List<BarterGoodsClassify> selectClassifyByParentId(long parentId);

    /**
     * 根据父Id列出所有子商品
     * @param pageForm
     * @param parentId
     * @return
     */
    PageInfo<BarterGoodsInfo> queryByParentId(PageForm pageForm, long parentId);

    /**
     * 分类树
     * @return
     */
    List<BarterGoodsClassify> selectTree();

    /**
     * 添加商品分类
     * @param barterGoodsClassify
     * @return
     */
    int addClassify(BarterGoodsClassify barterGoodsClassify);

    /**
     * 删除商品分类
     * @param classifyId
     * @return
     */
    int deleteClassifyById(long classifyId);

    /**
     * 更新商品分类
     * @param barterGoodsClassify
     * @return
     */
    int updateClassify(BarterGoodsClassify barterGoodsClassify);

    BarterGoodsClassify selectClassifyById(long classifyId);

    /**
     * 发布商品
     * @param goodsReleasedFrom
     * @return
     */
    int addGoodsRelease(GoodsReleasedFrom goodsReleasedFrom);


}
