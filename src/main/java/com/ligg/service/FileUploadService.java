package com.ligg.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    //图片上传
    String uploadImage(MultipartFile file);
    //视频上传
    String uploadVideo(MultipartFile video);
}
