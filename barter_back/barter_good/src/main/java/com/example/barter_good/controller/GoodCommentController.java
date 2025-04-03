package com.example.barter_good.controller;


import com.example.barter_good.common.util.ResultInfoUtil;
import com.example.barter_good.domain.entity.BarterGoodsComment;
import com.example.barter_good.domain.form.PageForm;
import com.example.barter_good.lang.ResultInfo;
import com.example.barter_good.service.GoodCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName GoodCommentController
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-12-22 16:08
 * @Version 1.0
 **/
@RestController
@Api(tags = "用户评论")
@RequestMapping("/comment")
public class GoodCommentController {
    @Autowired
    private GoodCommentService goodCommentService;

    @ApiOperation("插入评论")
    @PostMapping()
    public ResultInfo adAdd(@RequestBody @Validated BarterGoodsComment barterGoodsCommit){
        if(0 < goodCommentService.addComment(barterGoodsCommit)){
            return ResultInfoUtil.buildSuccess("添加成功");
        }else {
            return ResultInfoUtil.buildError("添加失败");
        }
    }

    @ApiOperation("分页查询评论")
    @GetMapping("/page")
    public ResultInfo queryAdByPage(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("goodsId") int goodsId
    ){
        PageForm pageForm = new PageForm(pageNum,pageSize);
        return ResultInfoUtil.buildSuccess(goodCommentService.queryGoodsComment(pageForm,goodsId));
    }
}
