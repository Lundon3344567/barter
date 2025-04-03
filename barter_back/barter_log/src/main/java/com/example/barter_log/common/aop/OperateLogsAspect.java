package com.example.barter_log.common.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.barter_authority.service.UserService;
import com.example.barter_log.common.annotation.DoLog;
import com.example.barter_log.common.lang.LogConstants;
import com.example.barter_log.common.util.IpAddressUtil;
import com.example.barter_log.domain.entity.BarterExcDaily;
import com.example.barter_log.domain.entity.BarterOperateDaily;
import com.example.barter_log.service.BarterExcDailyService;
import com.example.barter_log.service.BarterOperateDailyService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author walnut
 * @version 1.0
 * @className DoLogsAspect
 * @description 日志操作切面
 * @date 2021/11/22 2:45 下午
 */
@Aspect
@Component
public class OperateLogsAspect {

    @Autowired
    private BarterOperateDailyService operateDailyService;

    @Autowired
    private BarterExcDailyService excDailyService;

    @Autowired
    private UserService userService;

    /**
     * 设置操作日志切入点 记录操作日志 (在注解位置会切入代码)
     */
    @Pointcut("@annotation(com.example.barter_log.common.annotation.DoLog)")
    public void doLogPointCut() {
    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* com.walnut.barter.controller..*.*(..))")
    public void operateExceptionLogPointCut() {
    }

    /**
     * @return void
     * @MethodName saveDoLog
     * @params joinPoint 切入点
     * @params keys 返回结果
     * @Description 记录普通操作日志, 此方法需要在调用方法执行完毕后执行
     * @Author: walnut
     * @Date: 2021/11/22
     */
    @AfterReturning(value = "doLogPointCut()", returning = "keys")
    public void saveDoLog(JoinPoint joinPoint, Object keys) {
        //获取request
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest reuqest = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //创建操作日志对象
        BarterOperateDaily operateDaily = new BarterOperateDaily();
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //设置pojo参数
            operateDaily.setOperateId(uuid);
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            //获取切入点的方法
            Method method = methodSignature.getMethod();
            DoLog DoLog = method.getAnnotation(DoLog.class);
            //获取操作
            if (DoLog != null) {
                String operateModule = DoLog.operateModule();
                String operateDesc = DoLog.operateDesc();
                String operateType = DoLog.operateType();
                //设置pojo参数
                operateDaily.setOperateDesc(operateDesc);
                operateDaily.setOperateType(operateType);
                operateDaily.setOperateModul(operateModule);
            }
            //获取请求的类名
            String className = joinPoint.getClass().getName();
            //获取方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            operateDaily.setOperateMethod(methodName);

            //获取请求参数
            String requestStyle = DoLog.requestStyle();
            if (requestStyle.equals(LogConstants.REQUEST_RESTFUL_STYLE)) {
                Map attribute = (Map) reuqest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                String args = JSONObject.toJSONString(joinPoint.getArgs());
                operateDaily.setOperateReqParam(args);
            } else if (requestStyle.equals(LogConstants.REQUEST_COMMON_STYLE)) {
                Map<String, String[]> parameterMap = reuqest.getParameterMap();
                operateDaily.setOperateReqParam(JSON.toJSONString(parameterMap));

//                Map<String, String> map = MapUtils.converMap(parameterMap);
//                operateDaily.setOperateReqParam(JSON.toJSONString(map));
            } else {
                Map<String, String> resultMap = new HashMap<String, String>();
                Map attribute = (Map) reuqest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                String restfulParam = JSON.toJSONString(attribute);
                resultMap.put(LogConstants.REQUEST_RESTFUL_STYLE, restfulParam);

                Map<String, String[]> parameterMap = reuqest.getParameterMap();
                String kvParam = JSON.toJSONString(parameterMap);

//                Map<String, String> map = MapUtils.converMap(parameterMap);
//                String kvParam = JSON.toJSONString(map);

                resultMap.put(LogConstants.REQUEST_COMMON_STYLE, kvParam);

                operateDaily.setOperateReqParam(JSON.toJSONString(resultMap));
            }
            Object principal = null;
            try {
                principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            } catch (Exception e) {
                principal = null;
            }
            String username = "";
            long id;
            try {
                username = ((UserDetails) principal).getUsername();
                id = userService.queryIdByUsername(username);
            } catch (ClassCastException e) {
                username = "anonymousUser";
                id = -1;
            }

            // 获取用户ID
            operateDaily.setOperateUserId(Long.toString(id));
            // 设置用户名称
            operateDaily.setOperateUserName(username);
            // 获取请求IP
            operateDaily.setOperateIp(IpAddressUtil.getIpAddr(reuqest));
            // 设置请求URI
            operateDaily.setOperateUri(reuqest.getRequestURI());
            // 设置创建时间
            operateDaily.setOperateTime(new Date());
            //设置版本 (开发环境可以直接注释)
            operateDaily.setOperateVer("V0.1");
            operateDailyService.insert(operateDaily);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @MethodName: saveExceptionDaily
     * @Params: [joinPoint, throwable]
     * @return: void
     * @Description: 记录异常日志到数据库 第一个参数切入点,第二个参数异常信息,异常抛出后执行
     * @Author: walnut
     * @Date: 2021/11/22
     */
    @AfterThrowing(pointcut = "operateExceptionLogPointCut()", throwing = "throwable")
    public void saveExceptionDaily(JoinPoint joinPoint, Throwable throwable) {
        //获取request
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest reuqest = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //创建异常日志对象
        BarterExcDaily excDaily = new BarterExcDaily();
        try {
            //从切入点反射获取切入点的Method
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            excDaily.setExcId(UUID.randomUUID().toString().replaceAll("-", ""));
            //设置请求的方法
            String className = joinPoint.getClass().getName();
            String methodName = method.getName();
            methodName = className + "." + methodName;
            excDaily.setOperateMethod(methodName);
            DoLog DoLog = method.getAnnotation(DoLog.class);
            //获取请求参数
            String requestStyle;
            try {
                requestStyle = DoLog.requestStyle();
            } catch (Exception e) {
                requestStyle = "";
            }
            if (requestStyle.equals(LogConstants.REQUEST_RESTFUL_STYLE)) {
                assert reuqest != null;
                Map attribute = (Map) reuqest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                excDaily.setExcReqParam(JSON.toJSONString(attribute));
            } else if (requestStyle.equals(LogConstants.REQUEST_COMMON_STYLE)) {

                Map<String, String[]> parameterMap = reuqest.getParameterMap();
                excDaily.setExcReqParam(JSON.toJSONString(parameterMap));


            } else {
                Map<String, String> resultMap = new HashMap<String, String>();
                assert reuqest != null;
                Map attribute = (Map) reuqest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                String restfulParam = JSON.toJSONString(attribute);
                resultMap.put(LogConstants.REQUEST_RESTFUL_STYLE, restfulParam);

                Map<String, String[]> parameterMap = reuqest.getParameterMap();
                String kvParam = JSON.toJSONString(parameterMap);

                resultMap.put(LogConstants.REQUEST_COMMON_STYLE, kvParam);

                excDaily.setExcReqParam(JSON.toJSONString(resultMap));
            }
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = "";
            long id;
            try {
                username = ((UserDetails) principal).getUsername();
                id = userService.queryIdByUsername(username);
            } catch (ClassCastException e) {
                username = "anonymousUser";
                id = -1;
            }
            //设置异常id
            excDaily.setExcId(uuid);
            //设置异常名称
            excDaily.setExcName(throwable.getClass().getName());
            //设置异常信息
            excDaily.setExcMes(stackTraceToString(throwable.getClass().getName(), throwable.getMessage(), throwable.getStackTrace()));


            // 获取用户ID
            excDaily.setOperateUserId(Long.toString(id));
            // 设置用户名称
            excDaily.setOperateUserName(username);
            // 获取请求IP
            excDaily.setOperateIp(IpAddressUtil.getIpAddr(reuqest));
            // 设置请求URI
            excDaily.setOperateUri(reuqest.getRequestURI());
            // 设置创建时间
            excDaily.setOperateTime(new Date());
            //设置版本 (开发环境可以直接注释)
            excDaily.setOperateVer("V0.1");
            excDailyService.insert(excDaily);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer sb = new StringBuffer();
        for (StackTraceElement element : elements) {
            sb.append(element).append("\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + sb.toString();
        return message;
    }


}


