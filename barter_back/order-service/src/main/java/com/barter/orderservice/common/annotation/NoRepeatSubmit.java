package com.barter.orderservice.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName NoRepeatSubmit
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-12-14 16:50
 * @Version 1.0
 **/
@Target(ElementType.METHOD) // 作用到方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface NoRepeatSubmit {
}
