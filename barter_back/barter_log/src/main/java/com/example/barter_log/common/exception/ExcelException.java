package com.example.barter_log.common.exception;

/**
 * @author walnut
 * @version 1.0
 * @className ExcelException
 * @description 表格类转换异常描述类
 * @date 2021/11/22 6:58 下午
 */
public class ExcelException extends Exception {

    public ExcelException() {
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }


}