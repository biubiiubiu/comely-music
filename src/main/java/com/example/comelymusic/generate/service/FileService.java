package com.example.comelymusic.generate.service;

import com.example.comelymusic.generate.dto.FileSourceDto;
import com.example.comelymusic.generate.entity.FileEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

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
    FileSourceDto uploadFile(MultipartFile multipartFile);
}
