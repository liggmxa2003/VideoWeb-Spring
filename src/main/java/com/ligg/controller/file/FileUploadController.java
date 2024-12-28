package com.ligg.controller.file;

import com.ligg.pojo.Result;
import com.ligg.service.FileUploadService;
import com.ligg.utils.QiNiuOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    // 视频文件上传接口
    @PostMapping("/uploadVideo")
    public ResponseEntity<Result<String>> handleVideoUpload(@RequestParam("video") MultipartFile video){
            String videoUrl = fileUploadService.uploadVideo(video);
            // 返回统一的响应结果，视频Url地址
            return ResponseEntity.ok(Result.success(videoUrl));
    }

    //图片上传接口
    @PostMapping("/uploadImage")
    public ResponseEntity<Result<String>> handleImageUpload(@RequestParam("image") MultipartFile file) {
        String imageUrl = fileUploadService.uploadImage(file);
        // 返回统一的响应结果
        return ResponseEntity.ok(Result.success(imageUrl));

    }
}
