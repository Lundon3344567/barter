package com.example.barter_log.common.annotation;


import com.example.barter_log.common.lang.LogConstants;

import java.lang.annotation.*;

/**
 * @author walnut
 * @version 1.0
 * @className DoLog
 * @description 用于日志打印相关的注解类DoLog
 * @date 2021/11/22 2:38 下午
 */
@Target(ElementType.METHOD)//注解作用范围(这里使用方法级别)
@Retention(RetentionPolicy.RUNTIME)//注解在方法运行阶段执行
@Documented//指明修饰的注解，可以被例如javadoc此类的工具文档化，只负责标记，没有成员取值
public @interface DoLog {
    /**
     *操作模块
     */
    public String operateModule() default "";

    /**
     *操作类型
     */
    public String operateType() default "";

    /**
     *操作描述
     */
    public String operateDesc() default "";
    /**
     * 请求风格(默认为普通请求风格)
     */
    public String requestStyle() default LogConstants.REQUEST_RESTFUL_COMMON_STYLE;

}

