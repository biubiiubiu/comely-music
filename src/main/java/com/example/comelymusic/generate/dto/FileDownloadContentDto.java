package com.example.comelymusic.generate.dto;

import com.example.comelymusic.generate.entity.FileEntity;
import lombok.Data;

/**
 * description: 下载文件返回数据模板
 *
 * @author: zhangtian
 * @since: 2022-04-11 16:40
 */
@Data
public class FileDownloadContentDto {
    private FileEntity fileEntityInfo;
    private byte[] fileContent;
}
