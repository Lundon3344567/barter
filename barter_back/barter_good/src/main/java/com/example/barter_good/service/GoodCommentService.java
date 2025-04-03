package com.example.barter_good.service;


import com.example.barter_good.domain.entity.BarterGoodsComment;
import com.example.barter_good.domain.form.PageForm;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * @ClassName GoodCommentService
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-12-22 15:38
 * @Version 1.0
 **/
@Service
public interface GoodCommentService {

    int addComment(BarterGoodsComment barterGoodsCommit);

    PageInfo<BarterGoodsComment> queryGoodsComment(PageForm pageForm, long goodsId);
}
