package com.example.comelymusic.generate.controller.responses;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import lombok.Data;

/**
 * description: OSS文件服务凭证，前端凭借这个可以上传下载文件
 *
 * @author: zhangtian
 * @since: 2022-04-12 16:20
 */
@Data
public class OssTokenResponse {
    private String requestId;
    private String endpoint;
    private String bucketName;
    private String securityToken;
    private String accessKeySecret;
    private String accessKeyId;
    private String expiration;

    public OssTokenResponse setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public OssTokenResponse setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public OssTokenResponse setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public OssTokenResponse setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
        return this;
    }

    public OssTokenResponse setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
        return this;
    }

    public OssTokenResponse setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
        return this;
    }

    public OssTokenResponse setExpiration(String expiration) {
        this.expiration = expiration;
        return this;
    }
}
