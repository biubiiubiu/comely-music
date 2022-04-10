package com.example.comelymusic.generate.controller.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

/**
 * description: 文件上传约束
 *
 * @author: zhangtian
 * @since: 2022-03-28 16:33
 */
@Data
public class FileUploadRequest {

    @NotBlank(message = "文件名不能为空")
    private String name;

    private Long size;

    @NotBlank(message = "后缀名不能为空")
    private String ext;

    @NotBlank(message = "key不能为空")
    private String key;

    @NotBlank(message = "key不能为空")
    private MultipartFile multipartFile;
}

