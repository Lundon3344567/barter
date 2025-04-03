package com.example.barter_authority.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author walnut
 * @version 1.0
 * @className Json
 * @description 转换json
 * @date 2021/11/16 5:33 下午
 */
public class JsonParamUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JsonParamUtil.class);

    public static String getJsonParam(HttpServletRequest request) {

        String jsonParam = "";
        ServletInputStream inputStream = null;
        try {
            int contentLength = request.getContentLength();
            if (!(contentLength < 0)) {
                byte[] buffer = new byte[contentLength];
                inputStream = request.getInputStream();
                for (int i = 0; i < contentLength; ) {
                    int len = inputStream.read(buffer, i, contentLength);
                    if (len == -1) {
                        break;
                    }
                    i += len;
                }
                jsonParam = new String(buffer, "utf-8");
            }
        } catch (IOException e) {
            LOG.error("参数转换成json异常g{}", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOG.error("参数转换成json异常s{}", e);
                }
            }
        }
        return jsonParam;
    }

}

