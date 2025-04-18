package com.barter.qiniuyunservice.service.impl;


import com.barter.qiniuyunservice.common.util.StringUtil;
import com.barter.qiniuyunservice.config.QiNiuYunConfig;
import com.barter.qiniuyunservice.service.UploadImageService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @ClassName UploadImageServiceImpl
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-17 17:11
 * @Version 1.0
 **/


@Service
public class UploadImageServiceImpl implements UploadImageService {

    private QiNiuYunConfig qiNiuYunConfig;

    /**七牛文件上传管理器*/
    private UploadManager uploadManager;
    /**上传的token*/
    private String token;
    /**七牛认证管理*/
    private Auth auth;

    private BucketManager bucketManager;

    public UploadImageServiceImpl(QiNiuYunConfig qiNiuYunConfig) {
        this.qiNiuYunConfig = qiNiuYunConfig;
        init();
    }

    private void init() {
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择

        uploadManager = new UploadManager(new Configuration(Zone.zone2()));
        auth = Auth.create(qiNiuYunConfig.getAccessKey(), qiNiuYunConfig.getSecretKey());
        // 根据命名空间生成的上传token
        bucketManager = new BucketManager(auth, new Configuration(Zone.zone2()));
        token = auth.uploadToken(qiNiuYunConfig.getBucketName());
    }


    /**
     * 上传文件
     * @param file
     * @return
     */
    @Override
    public String uploadQNImg(MultipartFile file) {
        String resultImage = "失败";
        try {
            // 判断图片后缀，并使用工具类根据上传文件生成唯一图片名称,防止截断字符如“%00”
            String fileName = file.getOriginalFilename();
            String imgName = StringUtil.getRandomImgName(fileName);

            //判断是否为恶意程序
            //通过流的方式把文件转换为BufferedImage对象,获取宽和高,只有图片才具有宽高属性
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if(bufferedImage.getHeight()==0 || bufferedImage.getWidth()==0){
                return resultImage;
            }

            // 上传图片文件
            Response res = uploadManager.put(file.getInputStream(), imgName, token, null, null);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res.toString());
            }
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);

            // 直接返回外链地址
            return getPrivateFile(imgName);
        } catch (QiniuException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "失败";
    }

    /**
     * 获取私有空间文件
     *
     * @param fileKey
     * @return
     */
    @Override
    public String getPrivateFile(String fileKey) {
        String encodedFileName = null;
        String finalUrl = null;
        try {
            encodedFileName = URLEncoder.encode(fileKey, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", this.qiNiuYunConfig.getUrl(), encodedFileName);
            //1小时，可以自定义链接过期时间
            long expireInSeconds = 3600;
            finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return finalUrl;
    }


    /**
     * 根据空间名、文件名删除文件
     * @param bucketName
     * @param fileKey
     * @return
     */
    @Override
    public boolean removeFile(String bucketName, String fileKey) {
        try {
            bucketManager.delete(bucketName, fileKey);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return true;
    }

}
