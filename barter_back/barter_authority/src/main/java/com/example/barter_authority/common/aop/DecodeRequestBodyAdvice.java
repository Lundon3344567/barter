package com.example.barter_authority.common.aop;

import com.example.barter_authority.common.annotation.AesSecurityParameter;
import com.example.barter_authority.common.util.AesEncryptUtils;
import com.example.barter_authority.common.util.RSAUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author walnut
 * @version 1.0
 * @className DoLog
 * @description 请求数据解密
 * @date 2021/12/11 2:38 下午
 */
@ControllerAdvice
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(DecodeRequestBodyAdvice.class);

    @Value("${server.private.key}")
    private String SERVER_PRIVATE_KEY;


    @Value("${aes.private.key}")
    private String AES_PRIVATE_KEY;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        try {
            if (methodParameter.getMethod().isAnnotationPresent(AesSecurityParameter.class)) {
                //获取注解配置的包含和去除字段
                AesSecurityParameter serializedField = methodParameter.getMethodAnnotation(AesSecurityParameter.class);
                //入参是否需要解密
                if (serializedField.inDecode()) {
                    logger.info("注解AesSecurityParameter,对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密");
//                    logger.info(inputMessage.toString());
                    return new AesHttpInputMessage(inputMessage);
                }
            }
            return inputMessage;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密出现异常：" + e.getMessage());
            return inputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    class MyHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;

        public MyHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            this.body = IOUtils.toInputStream(easpString(IOUtils.toString(inputMessage.getBody(), "utf-8")));
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        /**
         * @param requestData
         * @return
         */
        public String easpString(String requestData) {
            if (requestData != null && !requestData.equals("")) {
                Map<String, String> requestMap = new Gson().fromJson(requestData, new TypeToken<Map<String, String>>() {
                }.getType());
                // 密文
                String data = requestMap.get("requestData");
                // 加密的ase秘钥
                String encrypted = requestMap.get("encrypted");
                if (StringUtils.isEmpty(data) || StringUtils.isEmpty(encrypted)) {
                    throw new RuntimeException("参数【requestData】缺失异常！");
                } else {
                    String content = null;
                    String aseKey = null;
                    try {
                        aseKey = RSAUtils.decryptDataOnJava(encrypted, SERVER_PRIVATE_KEY);
                    } catch (Exception e) {
                        throw new RuntimeException("参数【aseKey】解析异常！");
                    }
                    try {
                        content = AesEncryptUtils.decrypt(data, aseKey);
                    } catch (Exception e) {
                        throw new RuntimeException("参数【content】解析异常！");
                    }
                    if (StringUtils.isEmpty(content) || StringUtils.isEmpty(aseKey)) {
                        throw new RuntimeException("参数【requestData】解析参数空指针异常!");
                    }
                    return content;
                }
            }
            throw new RuntimeException("参数【requestData】不合法异常！");
        }
    }

    class RsaHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;

        public RsaHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            this.body = IOUtils.toInputStream(easpString(IOUtils.toString(inputMessage.getBody(), "utf-8")));
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        /**
         * @param requestData
         * @return
         */
        public String easpString(String requestData) {
            if (requestData != null && !requestData.equals("")) {
                Map<String, String> requestMap = new Gson().fromJson(requestData, new TypeToken<Map<String, String>>() {
                }.getType());
                // 密文
                String data = requestMap.get("requestData");
                if (StringUtils.isEmpty(data)) {
                    throw new RuntimeException("参数【requestData】缺失异常！");
                } else {
                    String content = null;
                    try {
                        content = RSAUtils.decryptDataOnJava(data, SERVER_PRIVATE_KEY);
                    } catch (Exception e) {
                        throw new RuntimeException("参数【aseKey】解析异常！");
                    }
                    try {
                    } catch (Exception e) {
                        throw new RuntimeException("参数【content】解析异常！");
                    }
                    if (StringUtils.isEmpty(content)) {
                        throw new RuntimeException("参数【requestData】解析参数空指针异常!");
                    }
                    return content;
                }
            }
            throw new RuntimeException("参数【requestData】不合法异常！");
        }
    }

    class AesHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;


        public AesHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            this.body = IOUtils.toInputStream(easpString(IOUtils.toString(inputMessage.getBody(), "utf-8")));
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        /**
         * @param requestData
         * @return
         */
        public String easpString(String requestData) {
            if (requestData != null && !requestData.equals("")) {
                Map<String, String> requestMap = new Gson().fromJson(requestData, new TypeToken<Map<String, String>>() {
                }.getType());
                // 密文
                String data = requestMap.get("requestData");
                if (StringUtils.isEmpty(data)) {
                    throw new RuntimeException("参数【requestData】缺失异常！");
                } else {
                    String content = null;
                    try {
                        content = AesEncryptUtils.decrypt(data, AES_PRIVATE_KEY);
                    } catch (Exception e) {
                        throw new RuntimeException("参数【content】解析异常！");
                    }
                    if (StringUtils.isEmpty(content)) {
                        throw new RuntimeException("参数【requestData】解析参数空指针异常!");
                    }
                    return content;
                }
            }
            throw new RuntimeException("参数【requestData】不合法异常！");
        }
    }
}