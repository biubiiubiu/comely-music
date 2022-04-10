package com.example.comelymusic.generate.common.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;

/**
 * description: OSS服务连接配置
 *
 * @author: zhangtian
 * @since: 2022-04-10 22:50
 */
public class MyFileConfig {
    @Value("${oss.endpoint}")
    public static String ENDPOINT;

    @Value("${oss.bucketName}")
    public static String BUCKET_NAME;

    @Value("${oss.accessKeyId}")
    private static String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private static String accessKeySecret;

    private MyFileConfig() {
    }

    public static String getENDPOINT() {
        return ENDPOINT;
    }

    public static String getBucketName() {
        return BUCKET_NAME;
    }

    public static OSS getOssClient() {
        return new OSSClientBuilder().build(ENDPOINT, accessKeyId, accessKeySecret);
    }

}
