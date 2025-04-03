package com.example.barter_authority.common.listener;

import com.example.barter_authority.common.annotation.AccessLimit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AccessLimitInterceptor
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-12-07 14:51
 * @Version 1.0
 **/
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (!method.isAnnotationPresent(AccessLimit.class)) {
                return true;
            }
            AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int limit = accessLimit.limit();
            int sec = accessLimit.sec();
            String key = request.getRequestURI();
            try {
                Integer maxLimit = redisTemplate.opsForValue().get(key);
                if (maxLimit == null) {
                    //set时一定要加过期时间
                    redisTemplate.opsForValue().set(key, 1, sec, TimeUnit.SECONDS);
                } else if (maxLimit < limit) {
                    //方案一:
                    redisTemplate.opsForValue().set(key, maxLimit + 1, Objects.requireNonNull(redisTemplate.getExpire(key)), TimeUnit.SECONDS);
                } else {
                    System.out.println("lala");
                    //ResponseUtil.addVaryFieldName(response, "请求过于频繁,请稍后再试!");
                    return false;
                }
            } catch (NullPointerException e) {
                redisTemplate.opsForValue().set(key, 1, sec, TimeUnit.SECONDS);
            }
        }
        return true;

    }
}
