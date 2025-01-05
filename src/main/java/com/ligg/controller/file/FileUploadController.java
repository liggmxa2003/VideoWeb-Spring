package com.ligg.controller.file;

import com.ligg.pojo.Result;
import com.ligg.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    // 文件上传存储的根目录
    private static final String UPLOAD_DIR = "uploads";
    // 视频文件上传接口
    @PostMapping("/uploadVideo")
    public Result<String> handleVideoUpload(@RequestParam("video") MultipartFile video) {
        String videoUrl = fileUploadService.uploadVideo(video);
        // 返回统一的响应结果，视频Url地址
        return Result.success(videoUrl);
    }

    //图片上传接口
    @PostMapping("/uploadImage")
    public Result<String> handleImageUpload(@RequestParam("image") MultipartFile file) {
        String imageUrl = fileUploadService.uploadImage(file);
        // 返回统一的响应结果
        return Result.success(imageUrl);

    }

    /**
     * 上传文件切片
     *
     * @param file        切片文件
     * @param chunkIndex  当前切片索引
     * @param totalChunks 总切片数
     * @param fileId      文件唯一标识
     * @return 上传结果
     */
    @PostMapping("/chunk")
    public Result<String> uploadChunk(
            @RequestParam("file") MultipartFile file,
            @RequestParam("chunkIndex") int chunkIndex,
            @RequestParam("totalChunks") int totalChunks,
            @RequestParam("fileId") String fileId) {
        try {
            fileUploadService.saveChunk(file, fileId, chunkIndex);
            return Result.success();
        } catch (IOException e) {
            return Result.error("Error saving chunk: " + e.getMessage());
        }
    }

    /**
     * 合并文件切片
     * @param requestParams 请求参数
     * @return
     */
    @PostMapping("/merge")
    public Result<String> mergeChunks(@RequestBody Map<String, Object>requestParams) {
        String fileId = (String) requestParams.get("fileId");
        String fileName = (String) requestParams.get("fileName");
        int totalChunks = (int) requestParams.get("totalChunks");
        try {
            String fileUrl = fileUploadService.mergeChunks(fileId, fileName, totalChunks);
            return Result.success(fileUrl);
        } catch (IOException e) {
            return Result.error("Error merging chunks: " + e.getMessage());
        }
    }
}
