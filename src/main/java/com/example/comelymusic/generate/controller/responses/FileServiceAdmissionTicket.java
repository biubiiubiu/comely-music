package com.example.comelymusic.generate.controller.responses;

import com.example.comelymusic.generate.common.config.MyFileConfig;
import lombok.Data;

/**
 * description: OSS文件服务凭证，前端凭借这个可以上传下载文件
 *
 * @author: zhangtian
 * @since: 2022-04-12 16:20
 */
@Data
public class FileServiceAdmissionTicket {
    private static final String endpoint = "oss-cn-huhehaote.aliyuncs.com";
    private static final String bucketName = "comely-music-bucket";
    private String accessKeyId;
    private String accessKeySecret;
}
