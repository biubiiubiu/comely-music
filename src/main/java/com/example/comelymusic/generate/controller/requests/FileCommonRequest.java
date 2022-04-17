package com.example.comelymusic.generate.controller.requests;

import lombok.Data;

/**
 * description: 一般file crud的request
 *
 * @author: zhangtian
 * @since: 2022-04-17 14:16
 */
@Data
public class FileCommonRequest {
    private String filename;
    private String fileKey;
    private String storageUrl;
    private Long size;
}
