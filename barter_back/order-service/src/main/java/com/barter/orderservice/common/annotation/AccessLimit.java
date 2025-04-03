package com.barter.orderservice.common.annotation;

import java.lang.annotation.*;

/**
 * @ClassName AccessLimit
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-12-07 14:42
 * @Version 1.0
 **/

@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    //标识 指定sec时间段内的访问次数限制
    int limit() default 5;

    //标识 时间段
    int sec() default 5;
}
