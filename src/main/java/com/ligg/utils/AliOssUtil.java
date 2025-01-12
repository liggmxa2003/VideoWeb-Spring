//package com.ligg.utils;
//
//import com.aliyun.oss.ClientException;
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import com.aliyun.oss.OSSException;
//import com.aliyun.oss.model.PutObjectRequest;
//import com.aliyun.oss.model.PutObjectResult;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.io.InputStream;
//
//// 阿里云OSS上传文件
//@Slf4j
//public class AliOssUtil {
//
//    @Value("${alioss.BUCKENAME}")
//    private String ENDPOINT;
//    @Value("${alioss.AccessKey_ID}")
//    private String AccessKey_ID;
//    @Value("${alioss.AccessKey_Secret}")
//    private String AccessKey_Secret;
//    @Value("${alioss.BUCKENAME}")
//    private String BUCKENAME;
//
//    private final OSS ossClient;
//
//    public AliOssUtil() {
//        this.ossClient = new OSSClientBuilder().build(ENDPOINT, AccessKey_ID, AccessKey_Secret);
//    }
//
//    public String uploadFile(String objectName, InputStream in) throws Exception {
//        String url = "";
//        try {
//            // 创建PutObjectRequest对象。
//            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKENAME, objectName, in);
//
//            // 上传字符串。
//            PutObjectResult result = ossClient.putObject(putObjectRequest);
//            // 获取上传文件的URL
//            url = "https://"+BUCKENAME+"."+ENDPOINT.substring(ENDPOINT.lastIndexOf("/")+1)+"/"+objectName;
//        } catch (OSSException oe) {
//           log.error("Caught an OSSException, which means your request made it to OSS, ");
//        } catch (ClientException ce) {
//           log.error("Caught an ClientException, which means the client encountered ");
//        } finally {
//
//        }
//        return url;
//    }
//}