package com.example.comelymusic.generate.controller.responses.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * description: 文件上传请求结果，包含storage_url
 *
 * @author: zhangtian
 * @since: 2022-04-13 16:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {
    /**
     * 这一批文件的ossToken
     */
    private OssTokenInfo ossTokenInfo;

    /**
     * 这一批文件的map(originalName,storageUrl)
     */
    private Map<String, String> fileStorageUrlMap;
}
