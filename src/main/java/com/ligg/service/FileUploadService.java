package com.ligg.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    //图片上传
    String uploadImage(MultipartFile file);
    //视频上传
    String uploadVideo(MultipartFile video);

    void saveChunk(MultipartFile file, String fileId, int chunkIndex) throws IOException;

    String mergeChunks(String fileId, String fileName, int totalChunks) throws IOException;
}
