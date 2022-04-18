package com.example.comelymusic.generate.controller.requests.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * description: 文件上传http格式约束
 *
 * @author: zhangtian
 * @since: 2022-04-11 20:46
 */
@Data
public class FileUploadRequest implements Serializable {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileUploadInfo{
        private String originalFilename;
        private Long size;
    }
    /**
     * 用户名
     */
    private String username;

    /**
     * 需要上传的文件信息
     */
    List<FileUploadInfo> fileUploadInfoList;

}
