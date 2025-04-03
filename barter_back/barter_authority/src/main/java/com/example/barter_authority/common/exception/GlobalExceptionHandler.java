package com.example.barter_authority.common.exception;

import com.example.barter_authority.common.enums.ResultEnum;
import com.example.barter_authority.common.lang.ResultInfo;
import com.example.barter_authority.common.util.ResultInfoUtil;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ValidationException;
import java.util.Objects;

/**
 * 全局异常处理配置类
 *
 * @author walnut
 * @version 1.0
 * @date 2021/11/6 8:40 下午
 */
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 参数异常捕获
     *
     * @param e 异常
     * @return 返回前端
     */
    @ExceptionHandler(ValidationException.class)
    public ResultInfo<String> bindExceptionHandler(ValidationException e){

        LOG.error("请检查参数: " + e.getMessage());

        return ResultInfoUtil.buildError(ResultEnum.VALIDATION_ERROR,"请检查参数: " +e.getMessage());
    }

    /**
     * 参数异常处理
     *
     * @param e 参数异常异常
     * @return 返回
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResultInfo<String> handler(RuntimeException e) {
        LOG.info("LOG==> " + String.format("%-12s", "e.getMessage()") + String.format("%-12s", e.getMessage()) + " :: info: 运行时异常");
        e.printStackTrace();
        System.out.println(e.getClass());
        return ResultInfoUtil.buildError( "运行时异常:"+ e.getMessage());
    }

    @ExceptionHandler(value = JwtException.class)
    public ResultInfo<String> handler(JwtException e) {
        LOG.info("LOG==> " + String.format("%-12s", "e.getMessage()") + String.format("%-12s", e.getMessage()) + " :: info: Token异常");
        e.printStackTrace();
        return ResultInfoUtil.buildError("Token:" + e.getMessage());
    }

    @ExceptionHandler(value = CaptchaException.class)
    public ResultInfo<String> handler(CaptchaException e) {
        LOG.info("LOG==> " + String.format("%-12s", "e.getMessage()") + String.format("%-12s", e.getMessage()) + " :: info: 验证码异常");
        e.printStackTrace();
        return ResultInfoUtil.buildError("Token:" + e.getMessage());

    }

    /**
     * 处理空指针的异常
     *
     * @param e 异常
     * @return 返回
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResultInfo<Enum<ResultEnum>> exceptionHandler(NullPointerException e) {
        LOG.error("发生空指针异常！原因是:", e);
        return ResultInfoUtil.buildError(ResultEnum.BODY_NOT_MATCH);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public ResultInfo<Enum<ResultEnum>> exceptionHandler(AccessDeniedException e) {
        LOG.error("权限不足:", e);
        return ResultInfoUtil.buildError(ResultEnum.ACCESS_DENIED);
    }

    /**
     *  拦截Valid数据校验不通过的报错
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultInfo<Void> notValidExceptionHandler(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        Objects.requireNonNull(bindingResult.getFieldError());
        return ResultInfoUtil.buildError(ResultEnum.ERROR,
                bindingResult.getFieldError().getField() + " " +
                        bindingResult.getFieldError().getDefaultMessage());
    }

    /**
     * 处理其他异常
     *
     * @param e 异常
     * @return 返回
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultInfo<Enum<ResultEnum>> exceptionHandler(Exception e) {
        //TODO 泛型修改
        LOG.error("未知异常！原因是:", e);
        return ResultInfoUtil.buildError(ResultEnum.INTERNAL_SERVER_ERROR, "未知异常");
    }
}
