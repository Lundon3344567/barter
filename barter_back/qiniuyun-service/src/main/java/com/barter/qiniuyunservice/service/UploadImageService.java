package com.barter.qiniuyunservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UploadImageService
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-17 17:09
 * @Version 1.0
 **/
public interface UploadImageService {

    /**
     * 上传文件
     * @param file
     * @return
     */
    String uploadQNImg(MultipartFile file);

    /**
     * 获取文件
     * @param fileKey
     * @return
     */
    String getPrivateFile(String fileKey);

    /**
     * 删除文件
     * @param bucketName
     * @param fileKey
     * @return
     */
    boolean removeFile(String bucketName, String fileKey);

}
