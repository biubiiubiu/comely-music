package com.example.comelymusic.generate.service;

import com.example.comelymusic.generate.controller.requests.FileCommonRequest;
import com.example.comelymusic.generate.controller.requests.FileUploadRequest;
import com.example.comelymusic.generate.controller.responses.FileUploadResponse;
import com.example.comelymusic.generate.controller.responses.OssTokenInfo;
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

    String getIdByFilename(String filename);
}
