package com.example.comelymusic.generate.controller.requests;

import lombok.Data;

import java.util.Map;

/**
 * description: 一般file crud的request
 *
 * @author: zhangtian
 * @since: 2022-04-17 14:16
 */
@Data
public class FileCommonRequest {
    @Data
    public static class CommonInfo {
        private String filename;
        private String storageUrl;
        private Long size;
    }

    /**
     * map(fileKey,commonInfo)
     */
    private Map<String, CommonInfo> fileKeyInfoMap;
}
