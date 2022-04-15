package com.example.comelymusic.generate.controller.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String storageUrl;
}
