package com.example.comelymusic.generate.service;

import com.example.comelymusic.generate.controller.requests.file.FileCommonRequest;
import com.example.comelymusic.generate.controller.requests.file.FileUploadRequest;
import com.example.comelymusic.generate.controller.responses.file.FileUploadResponse;
import com.example.comelymusic.generate.controller.responses.file.OssTokenInfo;
import com.example.comelymusic.generate.entity.FileEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
public interface FileService extends IService<FileEntity> {
    FileUploadResponse getUploadInfo(FileUploadRequest fileUploadRequest);
    Boolean saveUploadInfo(FileCommonRequest fileCommonRequest);
    OssTokenInfo getOssToken(String username);
}
