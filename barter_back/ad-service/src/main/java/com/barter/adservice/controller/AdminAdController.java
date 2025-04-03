package com.barter.adservice.controller;

//import com.barter.adservice.common.annotation.DoLog;
//import com.barter.adservice.common.lang.LogConstants;
//import com.barter.adservice.common.lang.OperateTypeContains;
//import com.barter.adservice.common.lang.ResultInfo;
//import com.barter.adservice.common.util.ResultInfoUtil;
import com.barter.adservice.common.lang.ResultInfo;
import com.barter.adservice.common.util.ResultInfoUtil;
import com.barter.adservice.domain.entity.BarterAdInfo;
import com.barter.adservice.domain.form.PageForm;
import com.barter.adservice.service.AdService;
//import com.barter.bartercommon.common.lang.ResultInfo;
//import com.barter.bartercommon.common.util.ResultInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AdminAdController
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-11 16:04
 * @Version 1.0
 **/
@RestController
@Api(tags = "管理员广告管理相关")
@RequestMapping("/adadmin/ad")
public class AdminAdController {

    @Autowired
    private AdService adService;

//    @PreAuthorize("hasAuthority('admin:ad:save')")
    @ApiOperation("插入广告")
    @PostMapping()
    //@DoLog(operateModule = "Ad", operateType = OperateTypeContains.OPERATE_INSERT, operateDesc = "插入广告", requestStyle = LogConstants.REQUEST_COMMON_STYLE)
    public ResultInfo adAdd(@RequestBody @Validated BarterAdInfo barterAdInfo){
        if(0 < adService.adAdd(barterAdInfo)){
            return ResultInfoUtil.buildSuccess("广告添加成功");
        }else {
            return ResultInfoUtil.buildError("广告添加失败");
        }
    }

    //@PreAuthorize("hasAuthority('admin:ad:delete')")
    @ApiOperation("通过广告id删除广告")
    @DeleteMapping("/{id}")
    //@DoLog(operateModule = "Ad", operateType = OperateTypeContains.OPERATE_DELETE, operateDesc = "删除广告", requestStyle = LogConstants.REQUEST_COMMON_STYLE)
    public ResultInfo adDeleteById(@PathVariable(name = "id") long adId){
        if(adService.adDeleteById(adId) > 0){
            return ResultInfoUtil.buildSuccess("广告删除成功");
        }else {
            return ResultInfoUtil.buildError("广告删除失败");
        }
    }

    //@PreAuthorize("hasAuthority('admin:ad:update')")
    @ApiOperation("修改广告")
    @PutMapping()
    public ResultInfo adModify(@RequestBody BarterAdInfo barterAdInfo){
        if(adService.adModify(barterAdInfo) > 0){
            return ResultInfoUtil.buildSuccess("广告修改成功");
        }else {
            return ResultInfoUtil.buildError("广告修改失败");
        }
    }

    @ApiOperation("分页查询所有广告")
    @GetMapping("/page")
    public ResultInfo queryAdByPage(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize
    ){
        PageForm pageForm = new PageForm(pageNum,pageSize);
        return ResultInfoUtil.buildSuccess(adService.queryAdByPageAll(pageForm));
    }

    @ApiOperation("分页查询最新广告")
    @GetMapping("/pages")
    public ResultInfo queryAdByPageOrderby(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize
    ){
        PageForm pageForm = new PageForm(pageNum,pageSize);
        return ResultInfoUtil.buildSuccess(adService.adSelectOrderBy(pageForm));
    }


    @ApiOperation("查询所有广告")
    @GetMapping("/ads")
    public ResultInfo queryAdByPage(){
        return ResultInfoUtil.buildSuccess(adService.adSelect());
    }

    @ApiOperation("通过id查询广告")
    @GetMapping("/{id}")
    public  ResultInfo selectAdById(@PathVariable(name = "id") long id){
        return ResultInfoUtil.buildSuccess(adService.selectAdById(id));
    }


}
