package com.example.comelymusic.generate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: OSS存储的文件资源信息
 *
 * @author: zhangtian
 * @since: 2022-04-10 23:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileSourceDto {
    private String originalFilename;
    private String filenameUUID;
    private String ext;
    private Integer size;
    private String type;
    /**
     * 在oss存储位置
     */
    private String storageUrl;
    /**
     * 文件访问路径，例：https：//ossxxxhuhehaote/uuid.jpg
     */
    private String visitUrl;
}
