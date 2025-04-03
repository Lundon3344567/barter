package com.example.barter_log.controller;

import com.example.barter_authority.domain.form.PageForm;
import com.example.barter_log.common.annotation.DoLog;
import com.example.barter_log.common.lang.LogConstants;
import com.example.barter_log.common.lang.OperateTypeContains;
import com.example.barter_log.common.lang.ResultInfo;
import com.example.barter_log.common.util.ResultInfoUtil;
import com.example.barter_log.domain.entity.BarterExcDaily;
import com.example.barter_log.domain.entity.BarterOperateDaily;
import com.example.barter_log.service.BarterExcDailyService;
import com.example.barter_log.service.BarterOperateDailyService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author walnut
 * @version 1.0
 * @className LogController
 * @description 日志操作Controller
 * @date 2021/11/22 2:46 下午
 */
@RestController
@RequestMapping("/logs")
@Api(tags = "管理员日志操作相关")
public class AdminLogController {
    @Autowired
    private BarterExcDailyService excDailyService;
    @Autowired
    private BarterOperateDailyService operateDailyService;

    @ResponseBody
    @ApiOperation("获取所有日志")
    @GetMapping("/operate/page")
    public ResultInfo<PageInfo<BarterOperateDaily>> selectAll(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize) {
        PageForm pageForm = new PageForm(pageNum, pageSize);
        PageInfo<BarterOperateDaily> resultList = operateDailyService.queryAllByPages(pageForm);
        return ResultInfoUtil.buildSuccess(resultList);
    }

    @ResponseBody
    @ApiOperation("查询日志")
    @GetMapping("/operate/condition")
    public ResultInfo<PageInfo<BarterOperateDaily>> select(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("type") String type) {

        PageForm pageForm = new PageForm(pageNum, pageSize);
        PageInfo<BarterOperateDaily> resultList = operateDailyService.queryByPages(pageForm,type);
        return ResultInfoUtil.buildSuccess(resultList);
    }


    @ResponseBody
    @ApiOperation("导出所有日志")
    @GetMapping("/operate/file")
    @DoLog(operateModule = "LOG", operateType = OperateTypeContains.OPERATE_SELECT, operateDesc = "导出所有日志", requestStyle = LogConstants.REQUEST_COMMON_STYLE)
    public void optFile(HttpServletResponse response, HttpServletRequest request) throws IOException {
        operateDailyService.export(response);
    }

    @ApiOperation("删除操作日志")
    @DeleteMapping("/operate")
    @DoLog(operateModule = "LOG", operateType = OperateTypeContains.OPERATE_DELETE, operateDesc = "删除所有操作日志", requestStyle = LogConstants.REQUEST_COMMON_STYLE)
    public ResultInfo<String> deleteOperateLog() {
        operateDailyService.deleteAllOperate();
        return ResultInfoUtil.buildSuccess("删除成功");
    }

    @ApiOperation("删除操作日志")
    @DeleteMapping("/exception")
    @DoLog(operateModule = "LOG", operateType = OperateTypeContains.OPERATE_DELETE, operateDesc = "删除所有操作日志", requestStyle = LogConstants.REQUEST_COMMON_STYLE)
    public ResultInfo<String> deleteExceptionLog() {
        operateDailyService.deleteAllException();
        return ResultInfoUtil.buildSuccess("删除成功");
    }

    @ResponseBody
    @ApiOperation("获取异常日志")
    @GetMapping("/exception/page")
    @DoLog(operateModule = "LOG", operateType = OperateTypeContains.OPERATE_SELECT, operateDesc = "分页查询所有异常日志", requestStyle = LogConstants.REQUEST_COMMON_STYLE)
    public ResultInfo<PageInfo<BarterExcDaily>> excAll(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize) {
        PageForm pageForm = new PageForm(pageNum, pageSize);
        PageInfo<BarterExcDaily> resultList = excDailyService.queryAllByPages(pageForm);
        return ResultInfoUtil.buildSuccess(resultList);
    }

    @ResponseBody
    @ApiOperation("导出异常日志")
    @GetMapping("/exception/file")
    @DoLog(operateModule = "LOG", operateType = OperateTypeContains.OPERATE_SELECT, operateDesc = "导出所有异常日志", requestStyle = LogConstants.REQUEST_COMMON_STYLE)
    public void excFile(HttpServletResponse response, HttpServletRequest request) throws IOException {
        excDailyService.export(response);
    }


}


