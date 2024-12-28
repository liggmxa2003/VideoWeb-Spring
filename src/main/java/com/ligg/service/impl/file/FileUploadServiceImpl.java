package com.ligg.service.impl.file;

import com.ligg.pojo.Result;
import com.ligg.service.FileUploadService;
import com.ligg.utils.QiNiuOssUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${qiniu.access-key}")
    private String accessKey;

    @Value("${qiniu.secret-key}")
    private String secretKey;

    @Value("${qiniu.bucket-name}")
    private String bucketName;

    @Value("${qiniu.domain}")
    private String domain; // 七牛云存储空间的域名

    @Value("${qiniu.video-path}")
    private String videoPrefox;//视频文件路径

    // 上传图片
    @Override
    public String uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            return ("文件为空");
        }
        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // 初始化七牛云工具类
            QiNiuOssUtil.init(accessKey, secretKey, bucketName);

            // 上传文件到七牛云
            String result = QiNiuOssUtil.uploadFile(inputStream, uniqueFileName);

            // 构建图片的 URL
            return domain + "/" + uniqueFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return ("上传失败: " + e.getMessage());
        }
    }
    // 上传视频
    @Override
    public String uploadVideo(MultipartFile video) {
        if (video.isEmpty()){
            return "视频文件为空";
        }
        try {
            // 获取文件输入流
            InputStream inputStream = video.getInputStream();
            // 生成唯一的文件名
            String originalFilename = video.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID() + fileExtension;
            // 初始化七牛云工具类
            QiNiuOssUtil.init(accessKey, secretKey, bucketName);
            // 上传文件到七牛云
            String result = QiNiuOssUtil.uploadVideo(inputStream,uniqueFileName);
            // 构建视频的 URL
            return domain + "/" + videoPrefox + uniqueFileName;
    }catch (IOException e){
            return "上传失败: " + e.getMessage();
        }
    }

}
