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
    private static final String endpoint = "sts.cn-huhehaote.aliyuncs.com";
    private static final String bucketName = "comely-music-bucket";

    /**
     * 包含Expiration、AccessKeyId、AccessKeySecret、SecurityToken、RequestId
     */
    private AssumeRoleResponse response;
}
