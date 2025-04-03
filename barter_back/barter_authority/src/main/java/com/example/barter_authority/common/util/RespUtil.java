package com.example.barter_authority.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author walnut
 * @version 1.0
 * @className RespUtil
 * @description 统一返回，在filter中处理返回
 * @date 2021/11/12 8:52 下午
 */
@Component
public class RespUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();


    public static void responseErr ( String msg, HttpServletResponse response) throws IOException {

        val map = Map.of("code","500","message",msg);
        val str = objectMapper.writeValueAsString(map);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.LOCKED.value());
        response.getWriter().print(str);
    }
}

