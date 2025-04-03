package com.example.barter_authority.common.aop;


import com.example.barter_authority.common.annotation.NoRepeatSubmit;
import com.example.barter_authority.common.util.ResultInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName NoRepeatSubmitAop
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-12-14 16:51
 * @Version 1.0
 **/
@Aspect
@Component
@Slf4j
public class NoRepeatSubmitAop {

    @Autowired
    private RedisTemplate<String, Integer> template;

    @Around("execution(* com.walnut..*Controller.*(..)) && @annotation(nrs)")
    public Object arround(ProceedingJoinPoint pjp, NoRepeatSubmit nrs) {
        ValueOperations<String, Integer> opsForValue = template.opsForValue();
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
            HttpServletRequest request = attributes.getRequest();
            String key = sessionId + "-" + request.getServletPath();
            //判断缓存中是否存在这个key,如果有则视为重复提交
            if (opsForValue.get(key) == null) {
                Object o = pjp.proceed();
                opsForValue.set(key, 0, 3, TimeUnit.SECONDS);
                return o;
            } else {
                log.debug("重复提交");
                System.out.println("重复");
                return ResultInfoUtil.build(401,"重复提交订单","稍后再试");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            log.debug("验证重复提交时出现未知异常!");
            return ResultInfoUtil.buildError("重复提交");
        }

    }
}
