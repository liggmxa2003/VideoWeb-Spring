package com.ligg.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.InputStream;

// 七牛云oss存储工具类
public class QiNiuOssUtil {
    // 七牛云存储的 Access Key 和 Secret Key
    private static String ACCESS_KEY;
    private static String SECRET_KEY;

    // 要上传的空间
    private static String BUCKET_NAME;

    // 创建上传对象
    private static UploadManager uploadManager;

    /**
     * 初始化七牛云工具类
     *
     * @param accessKey 七牛云 Access Key
     * @param secretKey 七牛云 Secret Key
     * @param bucketName 七牛云存储空间名称
     */
    public static void init(String accessKey, String secretKey, String bucketName) {
        ACCESS_KEY = accessKey;
        SECRET_KEY = secretKey;
        BUCKET_NAME = bucketName;
        uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
    }

    /**
     * 获取上传凭证
     */
    public static String getUpToken() {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return auth.uploadToken(BUCKET_NAME);
    }

    /**
     * 上传文件到七牛云
     *
     * @param filePath 文件路径
     * @return 上传结果
     * @throws QiniuException 异常处理
     */
    public static String uploadFile(String filePath) throws QiniuException {
        try {
            Response response = uploadManager.put(filePath, null, getUpToken());
            // 解析上传成功的结果
            return response.bodyString();
        } catch (QiniuException ex) {
            throw ex;
        }
    }

    /**
     * 上传内存中的图片数据到七牛云
     *
     * @param inputStream 图片输入流
     * @param key         文件名（可选）
     * @return 上传结果
     * @throws QiniuException 异常处理
     */
    public static String uploadFile(InputStream inputStream, String key) throws QiniuException {
        try {
            Response response = uploadManager.put(inputStream, key, getUpToken(), null, null);
            // 解析上传成功的结果
            return response.bodyString();
        } catch (QiniuException ex) {
            throw ex;
        }
    }

}
