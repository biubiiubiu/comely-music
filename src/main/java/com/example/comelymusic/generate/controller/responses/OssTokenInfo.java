package com.example.comelymusic.generate.controller.responses;

import lombok.Data;

/**
 * description: OSS文件服务凭证，前端凭借这个可以上传下载文件
 *
 * @author: zhangtian
 * @since: 2022-04-12 16:20
 */
@Data
public class OssTokenInfo {
    private String requestId;
    private String endpoint;
    private String bucketName;
    private String securityToken;
    private String accessKeySecret;
    private String accessKeyId;
    private String expiration;

    public OssTokenInfo setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public OssTokenInfo setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public OssTokenInfo setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public OssTokenInfo setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
        return this;
    }

    public OssTokenInfo setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
        return this;
    }

    public OssTokenInfo setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
        return this;
    }

    public OssTokenInfo setExpiration(String expiration) {
        this.expiration = expiration;
        return this;
    }
}
