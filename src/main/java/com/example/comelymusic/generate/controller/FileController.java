package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.dto.FileDownloadContentDto;
import com.example.comelymusic.generate.entity.FileEntity;
import com.example.comelymusic.generate.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

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
    @Autowired
    FileService fileService;

    /**
     * 上传文件，返回文件基本信息
     */
    @PostMapping("/upload")
    @ResponseBody
    public R uploadFile(@Validated @RequestBody MultipartFile multipartFile) throws IOException {
        FileEntity uploadResult = fileService.uploadFile(multipartFile);
        if (uploadResult != null) {
            HashMap<String, Object> resultInfo = new HashMap<>();
            resultInfo.put("fileInfo", uploadResult);
            return R.ok().message("上传成功！").data(resultInfo);
        }
        return R.error().message("上传失败！");
    }

    /**
     * 根据storageUrl下载文件，返回文件基本信息和byte[]
     */
    @PostMapping("/download")
    @ResponseBody
    public R downloadByStorageUrl(@Validated @RequestBody String storageUrl) {
        FileDownloadContentDto downloadResult = fileService.downloadFile(storageUrl);
        if (downloadResult != null) {
            HashMap<String, Object> resultInfo = new HashMap<>();
            resultInfo.put("fileInfo", downloadResult);
            return R.ok().message("下载成功！").data(resultInfo);
        }
        return R.error().message("下载失败！");
    }
}

