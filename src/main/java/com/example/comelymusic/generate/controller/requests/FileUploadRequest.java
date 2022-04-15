package com.example.comelymusic.generate.controller.requests;

import lombok.Data;

import java.io.Serializable;

/**
 * description: 文件上传http格式约束
 *
 * @author: zhangtian
 * @since: 2022-04-11 20:46
 */
@Data
public class FileUploadRequest implements Serializable {
    /**
     * test.jpg
     */
    private String originalFilename;

    /**
     * 750012 单位B
     */
    private Long size;
}
