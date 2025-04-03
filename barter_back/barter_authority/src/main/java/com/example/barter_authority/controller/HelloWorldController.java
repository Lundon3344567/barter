package com.example.barter_authority.controller;

import com.example.barter_authority.common.annotation.AesSecurityParameter;
import com.example.barter_authority.common.annotation.DoLog;
import com.example.barter_authority.common.lang.OperateTypeContains;
import com.example.barter_authority.common.lang.ResultInfo;
import com.example.barter_authority.common.util.ResultInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试接口
 * 示例接口
 * @Author walnut
 * @Date 2021/10/20 5:32 下午
 * @Version 1.0
 */
@RestController
@Api(tags = "测试及模板")
public class HelloWorldController {


    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);
    /**
     *
     * @return ResultInfo 接口返回统一格式
     * 使用ResultInfoUtil，方法有重写请自行查阅
     */
//    @PreAuthorize("hasRole('SUPERADMIN')")
//    @PreAuthorize("hasAuthority('admin:menu:list')")
    @PostMapping("/hello")
    @ApiOperation("hello接口")
    @AesSecurityParameter
    @DoLog(operateModule = "OPERATE", operateType = OperateTypeContains.OPERATE_VISIT, operateDesc = "测试普通操作日志")
    public ResultInfo hello(HttpServletRequest request){
//        int a = 1/0;
//        System.out.println(testVo.getId());
//        System.out.println(jsonObject);
        System.out.println(request);
        return ResultInfoUtil.buildSuccess("success");
    }

}
