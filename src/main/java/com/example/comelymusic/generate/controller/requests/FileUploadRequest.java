package com.example.comelymusic.generate.controller.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * description: 文件上传http格式约束
 *
 * @author: zhangtian
 * @since: 2022-04-11 20:46
 */
@Data
public class FileUploadRequest implements Serializable {
    String username;
    MultipartFile multipartFile;
}
