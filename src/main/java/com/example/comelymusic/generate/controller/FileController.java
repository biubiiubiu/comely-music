package com.example.comelymusic.generate.controller;


import com.aliyuncs.http.HttpRequest;
import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.requests.file.FileCommonRequest;
import com.example.comelymusic.generate.controller.requests.file.FileUploadRequest;
import com.example.comelymusic.generate.controller.responses.file.FileUploadResponse;
import com.example.comelymusic.generate.controller.responses.file.OssTokenInfo;
import com.example.comelymusic.generate.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @PostMapping("/upload-success")
    @ResponseBody
    public R setUploadSuccess(@RequestBody FileCommonRequest commonRequest) {
        Boolean isSuccess = fileService.saveUploadInfo(commonRequest);
        return R.ok().data(isSuccess);
    }

    /**
     * 获取用户的oss-token
     */
    @GetMapping("/oss-token/{username}")
    @ResponseBody
    public R getDownloadingInfo(@Validated @PathVariable("username") String username) {
        OssTokenInfo ossToken = fileService.getOssToken(username);
        return R.ok().data(ossToken);
    }

//    /**
//     * 测试
//     */
//    @PostMapping("/test")
//    @ResponseBody
//    public R test(HttpServletRequest request) {
//        String token = request.getHeader("Authorization");
//        return R.ok().data(token);
//    }
}

