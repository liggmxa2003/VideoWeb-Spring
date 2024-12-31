package com.ligg.service.impl.file;

import com.ligg.service.FileUploadService;
import com.ligg.utils.QiNiuOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

@Slf4j
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

    // 文件上传存储的根目录
    private static final String UPLOAD_DIR = "C:/uploads/";

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
        if (video.isEmpty()) {
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
            String result = QiNiuOssUtil.uploadVideo(inputStream, uniqueFileName);
            // 构建视频的 URL
            return domain + "/" + videoPrefox + uniqueFileName;
        } catch (IOException e) {
            return "上传失败: " + e.getMessage();
        }
    }


    /**
     * 保存文件切片到临时目录
     *
     * @param file       切片文件
     * @param fileId     文件唯一标识
     * @param chunkIndex 当前切片索引
     * @throws IOException 如果保存失败抛出异常
     */
    public void saveChunk(MultipartFile file, String fileId, int chunkIndex) throws IOException {
        String tempDir = UPLOAD_DIR + fileId + "_chunks/";
        File dir = new File(tempDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 保存切片文件
        File chunkFile = new File(tempDir + chunkIndex);
        file.transferTo(chunkFile);
    }

    /**
     * 合并切片文件为完整文件，并上传到七牛云，然后删除本地文件
     *
     * @param fileId      文件唯一标识
     * @param fileName    最终合并的文件名
     * @param totalChunks 总切片数
     * @throws IOException 如果合并或上传失败抛出异常
     */
    // 合并切片文件为完整文件，并上传到七牛云，然后删除本地文件
    public String mergeChunks(String fileId, String fileName, int totalChunks) throws IOException {
        String tempDir = UPLOAD_DIR + fileId + "_chunks/";
        File mergedFile = new File(UPLOAD_DIR + fileName);

        // 合并所有切片
        try (FileOutputStream out = new FileOutputStream(mergedFile)) {
            for (int i = 0; i < totalChunks; i++) {
                File chunkFile = new File(tempDir + i);
                if (!chunkFile.exists()) {
                    throw new IOException("Chunk file " + chunkFile.getAbsolutePath() + " does not exist.");
                }
                Files.copy(chunkFile.toPath(), out);
            }
        } catch (IOException e) {
            log.error("合并文件失败: {}", e.getMessage(), e);
            throw e;
        }
        // 删除临时目录及其内容
        File dir = new File(tempDir);
        if (dir.exists()) {
            for (File chunk : dir.listFiles()) {
                if (!chunk.delete()) {
                    log.error("Failed to delete chunk file: {}", chunk.getAbsolutePath());
                }
            }
            if (!dir.delete()) {
                log.error("删除失败{}", dir.getAbsolutePath());
            }
        }

        // 初始化七牛云工具类
        QiNiuOssUtil.init(accessKey, secretKey, bucketName);

        // 生成唯一的文件名
        String uniqueFileName = UUID.randomUUID().toString() + getFileExtension(fileName);

        // 将文件转换为输入流
        try (InputStream inputStream = new FileInputStream(mergedFile)) {
            // 上传文件到七牛云
            QiNiuOssUtil.uploadFile(inputStream, uniqueFileName);
        } catch (IOException e) {
            log.error("上传文件到七牛云失败: {}", e.getMessage(), e);
            // 删除已合并的本地文件
            if (mergedFile.exists()) {
                mergedFile.delete();
            }
            throw e;
        }

        // 构建文件的 URL
        String fileUrl = domain + "/" + uniqueFileName;

        // 删除本地合并后的文件
        if (mergedFile.exists()) {
            if (!mergedFile.delete()) {
                log.error("删除本地合并文件失败: {}", mergedFile.getAbsolutePath());
            }
        }


        // 返回文件的 URL
        return fileUrl;
    }
    private static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1 || lastIndexOfDot == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(lastIndexOfDot);
    }


}
