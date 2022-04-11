package com.example.comelymusic.generate.common.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: OSS服务连接配置
 *
 * @author: zhangtian
 * @since: 2022-04-10 22:50
 */
@Configuration
public class MyFileConfig {

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Bean
    OSS getOssClient() {
        return new OSSClientBuilder().build("oss-cn-huhehaote.aliyuncs.com", accessKeyId, accessKeySecret);
    }
}
