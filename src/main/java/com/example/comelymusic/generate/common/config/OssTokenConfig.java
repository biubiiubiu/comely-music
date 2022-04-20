package com.example.comelymusic.generate.common.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * description:
 *
 * @author: zhangtian
 * @since: 2022-04-12 22:10
 */
@Configuration
public class OssTokenConfig {
    @Value("${oss.token.accessKeyId}")
    private String accesskeyId;

    @Value("${oss.token.accessKeySecret}")
    private String accesskeySecret;

    private final static String roleArn = "acs:ram::1256516683186490:role/ramosstest";

    public final static long EFFECTIVE_TIME = 3600L;


    @Bean
    AssumeRoleResponse getRoleResponse() throws ClientException {
        DefaultProfile.addEndpoint("cn-huhehaote", "Sts", "sts.cn-huhehaote.aliyuncs.com");
        // 构造default profile。
        IClientProfile profile = DefaultProfile.getProfile("cn-huhehaote", accesskeyId, accesskeySecret);
        // 构造client。
        DefaultAcsClient client = new DefaultAcsClient(profile);
        final AssumeRoleRequest request = new AssumeRoleRequest();
        // 适用于Java SDK 3.12.0及以上版本。
        request.setSysMethod(MethodType.POST);
        request.setRoleArn(roleArn);
        request.setRoleSessionName("ztian");
        request.setDurationSeconds(EFFECTIVE_TIME); // 设置临时访问凭证的有效时间为1小时(最多一小时)
        return client.getAcsResponse(request);
    }
}
