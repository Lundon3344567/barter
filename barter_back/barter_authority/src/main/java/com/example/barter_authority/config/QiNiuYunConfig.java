package com.example.barter_authority.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**七牛云配置类  绑定配置文件
 * @ClassName QiNiuYunConfig
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-17 17:02
 * @Version 1.0
 **/


@Data
@Configuration
public class QiNiuYunConfig {
    /**
     * 七牛域名domain
     */
    @Value("${oss.qiniu.url}")
    private String url;
    /**
     * 七牛ACCESS_KEY
     */
    @Value("${oss.qiniu.accessKey}")
    private String AccessKey;
    /**
     * 七牛SECRET_KEY
     */
    @Value("${oss.qiniu.secretKey}")
    private String SecretKey;
    /**
     * 七牛空间名
     */
    @Value("${oss.qiniu.bucketName}")
    private String BucketName;



}
