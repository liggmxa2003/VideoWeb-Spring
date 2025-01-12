//package com.ligg;
//
//import com.aliyun.oss.*;
//import com.aliyun.oss.common.auth.*;
//import com.aliyun.oss.common.comm.SignVersion;
//import com.aliyun.oss.model.PutObjectRequest;
//import com.aliyun.oss.model.PutObjectResult;
//import java.io.ByteArrayInputStream;
//import java.io.FileInputStream;
//
//public class OssTest {
//
//    public static void main(String[] args) throws Exception {
//        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
//        String endpoint = "https://oss-cn-guangzhou.aliyuncs.com";
//        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
//        //EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
//        String AccessKey_ID="LTAI5tDV88FdCYow7KETP6AM";
//        String AccessKey_Secret="HKyrpQhwNGGEyX8NYern2B2boHPydx";
//        // 填写Bucket名称，例如examplebucket。
//        String bucketName = "animevideo";
//        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
//        String objectName = "artplayer_04_53.png";
//        // 填写Bucket所在地域。以华东1（杭州）为例，Region填写为cn-hangzhou。
//        String region = "cn-guangzhou";
//
//        // 创建OSSClient实例。
////        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
////        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
////        OSS ossClient = OSSClientBuilder.create()
////                .endpoint(endpoint)
////                .credentialsProvider(credentialsProvider)
////                .clientConfiguration(clientBuilderConfiguration)
////                .region(region)
////                .build();
//        OSS ossClient = new OSSClientBuilder().build(endpoint, AccessKey_ID, AccessKey_Secret);
//
//        try {
//            // 填写字符串。
//            String content = "Hello OSS，你好世界";
//
//            // 创建PutObjectRequest对象。
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new FileInputStream("C:\\Users\\LiGG\\Downloads\\artplayer_04_53.png"));
//
//            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
//            // ObjectMetadata metadata = new ObjectMetadata();
//            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//            // metadata.setObjectAcl(CannedAccessControlList.Private);
//            // putObjectRequest.setMetadata(metadata);
//
//            // 上传字符串。
//            PutObjectResult result = ossClient.putObject(putObjectRequest);
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//    }
//}