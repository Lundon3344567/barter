package com.barter.orderservice.common.enums;

import org.springframework.http.HttpStatus;

/**
 * 统一返回码枚举类
 * @author walnut
 * @date 2021/11/7 4:55 下午
 * @version 1.0
 */
public enum ResultEnum {
    //成功返回
    OK(HttpStatus.OK.value(), "成功"),
    //
    USER_NEED_AUTHORITIES(201, "用户未登录"),
    USER_LOGIN_FAILED(202, "用户账号或密码错误"),
    ACCESS_DENIED(411,"用户权限不足"),
    USER_LOGIN_SUCCESS(203, "用户登录成功"),
    USER_NO_ACCESS(410, "用户无权访问"),
    USER_LOGOUT_SUCCESS(205, "用户登出成功"),
    TOKEN_IS_BLACKLIST(206, "此token为黑名单"),
    LOGIN_IS_OVERDUE(207, "登录已失效"),
    ERROR(HttpStatus.BAD_REQUEST.value(), "失败"),
    BODY_NOT_MATCH(208,"空指针异常"),
    INTERNAL_SERVER_ERROR(209,"未知异常"),
    VALIDATION_ERROR(422,"参数异常")
    //TODO 在此定义更多的枚举对象

    ;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    ResultEnum() {
    }
    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static com.barter.orderservice.common.enums.ResultEnum parse(int code) {
        com.barter.orderservice.common.enums.ResultEnum[] values = values();
        for (com.barter.orderservice.common.enums.ResultEnum value : values) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new RuntimeException("Unknown code of ResultEnum");
    }
}
