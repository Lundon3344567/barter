package com.barter.qiniuyunservice.controller;


import com.barter.qiniuyunservice.domain.vo.QiniuVo;
import com.barter.qiniuyunservice.service.UploadImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UploadController
 * @Description TODO
 * @Author Flechazo_lalala
 * Date 2021-11-17 17:20
 * @Version 1.0
 **/
@Slf4j
@RestController
@Api(tags = "文件上传相关")
@RequestMapping("/qiniu")
public class UploadController {

    @Autowired
    private UploadImageService uploadImageService;

    @ApiOperation("上传文件")
    @PostMapping("/image")
    public String upLoadImage(@RequestParam("file") MultipartFile file){
        QiniuVo qiniuVo = new QiniuVo();
        if(!file.isEmpty()){
            String path = "http://"+uploadImageService.uploadQNImg(file);
            System.out.println("七牛云返回的图片链接是：" + path);
            qiniuVo.setPath(path);
            return path;
        }
        return "上传失败";
    }

    @GetMapping("/demo")
    public String demo(){
        return "成功";
    }

}