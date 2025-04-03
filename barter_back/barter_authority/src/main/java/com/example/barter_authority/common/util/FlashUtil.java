package com.example.barter_authority.common.util;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author walnut
 * @version 1.0
 * @className FlashUtil
 * @description TODO
 * @date 2021/12/22 00:20
 */
@Component
public class FlashUtil {

    @Resource
    private RedisUtil redisUtil;

    public void flash() {
        List<Object> toFlash = redisUtil.lGet("toFlash", 0, -1);

        for (Object flash : toFlash) {
            redisUtil.del(flash.toString());
        }
        redisUtil.del("toFlash");
    }

    public void add(String value) {
        try {
            redisUtil.lSet("toFlash",value,60*60);
        } catch (Exception e) {
            System.out.println("添加发生异常");
        }
    }


}
