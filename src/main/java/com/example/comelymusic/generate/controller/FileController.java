package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.requests.FileUploadRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@RestController
@RequestMapping("/generate/file")
public class FileController {

    @PostMapping("/upload_init")
    public R initUpload(@Validated @RequestBody FileUploadRequest fileUploadRequest) throws IOException {
        return R.ok().message("上传成功！");
    }
}

