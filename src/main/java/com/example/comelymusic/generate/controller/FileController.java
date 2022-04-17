package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.requests.FileUploadRequest;
import com.example.comelymusic.generate.controller.responses.FileUploadResponse;
import com.example.comelymusic.generate.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
     * 根据文件基本信息，生成Oss信息并返回
     */
    @PostMapping("/uploading")
    @ResponseBody
    public R getUploadingInfo(@Validated @RequestBody FileUploadRequest fileUploadRequestList) {
        FileUploadResponse uploadInfo = fileService.getUploadInfo(fileUploadRequestList);
        return R.ok().data(uploadInfo);
    }

    /**
     * 上传完成，刷新文件状态，并返回新的文件信息
     */
    @PutMapping("/upload-success")
    @ResponseBody
    public R setUploadSuccess() {
        return R.ok();
    }

    /**
     * 取消上传或者因为网络原因上传失败，删除mysql文件数据，并返回新的文件信息
     */
    @PutMapping("/upload-failed")
    @ResponseBody
    public R setUploadFailed() {
        return R.ok();
    }
}

