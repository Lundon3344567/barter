package com.barter.adservice.common.util;


import com.barter.adservice.common.enums.ResultEnum;
import com.barter.adservice.common.lang.ResultInfo;

/**
 * 统一返回格式的工具类，便于规范返回
 * @author walnut
 * @date 2021/11/4 9:22 下午
 * @version 1.0
 */
public class ResultInfoUtil {

    /**
     * 请求出错返回
     *
     */
    public static <T> ResultInfo<T> buildError() {
        return build(ResultEnum.ERROR, null);
    }

    public static <T> ResultInfo<T> buildError(ResultEnum resultEnum) {
        return build(resultEnum, null);
    }

    public static <T> ResultInfo<T> buildError(String message) {
        return build(ResultEnum.ERROR.getCode(), message, null);
    }


    public static <T> ResultInfo<T> buildError(ResultEnum resultEnum, String message) {
        return build(resultEnum.getCode(), message, null);
    }

    public static <T> ResultInfo<T> buildSuccess() {
        return build(ResultEnum.OK, null);
    }

    /**
     * 请求成功返回
     *
     */
    public static <T> ResultInfo<T> buildSuccess(T data) {
        return build(ResultEnum.OK, data);
    }
    public static <T> ResultInfo<T> buildSuccess(String message) {
        return build(ResultEnum.OK.getCode(), message, null);
    }
    public static <T> ResultInfo<T> build(ResultEnum resultEnum, T data) {
        return build(resultEnum.getCode(), resultEnum.getMsg(), data);
    }

    /**
     * 构建返回对象方法
     *
     */
    public static <T> ResultInfo<T> build(Integer code, String message, T data) {
        if (code == null) {
            code = ResultEnum.OK.getCode();
        }
        if (message == null) {
            message = ResultEnum.OK.getMsg();
        }

        ResultInfo<T> info = new ResultInfo<>();
        info.setCode(code);
        info.setMessage(message);
        info.setData(data);

        return info;
//        return
//
//        return ResultInfo.<T>builder()
//                .code(code)
//                .message(message)
////                .path(getCurrentUrl())
//                .data(data)
//                .build();
    }


}