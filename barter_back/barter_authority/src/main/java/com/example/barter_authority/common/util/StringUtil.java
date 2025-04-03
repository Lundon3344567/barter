package com.example.barter_authority.common.util;

import java.util.UUID;

/**七牛云生成唯一图片名称的工具类
 * @ClassName StringUtil
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-17 17:07
 * @Version 1.0
 **/
public class StringUtil {
    public static String getRandomImgName(String fileName) {

        int index = fileName.lastIndexOf(".");

        // 获取文件后缀
        String suffix = fileName.substring(index);

        //检验文件
        if(".jpg".equals(suffix) || ".jpeg".equals(suffix) || ".png".equals(suffix)){
            //改变上传到服务器的文件名  uuid + suffix
            // 生成UUID
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String path = uuid + suffix;
            return path;
        }else{
            throw new IllegalArgumentException();
        }
    }
}
