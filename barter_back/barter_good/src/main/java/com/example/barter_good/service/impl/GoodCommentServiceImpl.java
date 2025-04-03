package com.example.barter_good.service.impl;

import com.example.barter_good.domain.entity.BarterGoodsComment;
import com.example.barter_good.domain.form.PageForm;
import com.example.barter_good.mapper.BarterGoodsCommentMapper;
import com.example.barter_good.service.GoodCommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * @ClassName GoodCommentServiceImpl
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-12-22 15:39
 * @Version 1.0
 **/
@Service
public class GoodCommentServiceImpl implements GoodCommentService {
    @Autowired
    private BarterGoodsCommentMapper barterGoodsCommentMapper;

    @Override
    public int addComment(BarterGoodsComment barterGoodsComment) {
        barterGoodsComment.setCreatedTime(new Date());
        barterGoodsComment.setState(1);
        barterGoodsCommentMapper.insert(barterGoodsComment);
        return 1;
    }

    @Override
    public PageInfo<BarterGoodsComment> queryGoodsComment(PageForm pageForm, long goodsId) {
        PageHelper.startPage(pageForm.getRequestPage(),pageForm.getPageSize());
        List<BarterGoodsComment> barterGoodsCommentList = barterGoodsCommentMapper.queryByGoodsId(goodsId);
        return new PageInfo<>(barterGoodsCommentList);
    }
}
