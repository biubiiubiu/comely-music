package com.example.comelymusic.generate.service;

import com.example.comelymusic.generate.controller.requests.FileUploadRequest;
import com.example.comelymusic.generate.controller.responses.FileUploadResponse;
import com.example.comelymusic.generate.controller.responses.OssTokenResponse;
import com.example.comelymusic.generate.entity.FileEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
public interface FileService extends IService<FileEntity> {
    OssTokenResponse getOssToken();
    Map<String, FileUploadResponse> getUploadInfo(List<FileUploadRequest> fileUploadRequestList);
}
