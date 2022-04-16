package com.example.comelymusic.generate.service.impl;

import com.aliyun.oss.*;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.example.comelymusic.generate.common.ComelyMusicException;
import com.example.comelymusic.generate.controller.requests.FileUploadRequest;
import com.example.comelymusic.generate.controller.responses.FileUploadResponse;
import com.example.comelymusic.generate.controller.responses.OssTokenResponse;
import com.example.comelymusic.generate.entity.FileEntity;
import com.example.comelymusic.generate.enums.FileType;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.mapper.FileMapper;
import com.example.comelymusic.generate.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Service
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService {
    private static final String ENDPOINT = "oss-cn-huhehaote.aliyuncs.com";
    private final String BUCKET_NAME = "comely-music-bucket";

    @Autowired
    FileMapper fileMapper;

    @Autowired
    OSS ossClient;

    @Autowired
    AssumeRoleResponse roleResponse;

    /**
     * 获取STS凭证
     *
     * @return OssTokenResponse
     */
    @Override
    public OssTokenResponse getOssToken() {
        //todo 先检查redis有没有，没有的话新建一个token
        OssTokenResponse tokenResponse = new OssTokenResponse();
        String requestId = roleResponse.getRequestId();
        String accessKeyId = roleResponse.getCredentials().getAccessKeyId();
        String accessKeySecret = roleResponse.getCredentials().getAccessKeySecret();
        String securityToken = roleResponse.getCredentials().getSecurityToken();
        String expiration = roleResponse.getCredentials().getExpiration();
        tokenResponse.setRequestId(requestId)
                .setEndpoint(ENDPOINT)
                .setBucketName(BUCKET_NAME)
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setSecurityToken(securityToken)
                .setExpiration(expiration);
        return tokenResponse;
    }

    /**
     * 客户端请求上传文件，生成并返回需要上传的文件信息
     *
     * @param fileUploadRequestList 单个或多个文件
     * @return Map(originalFilename, storageUrl)
     */
    @Override
    public Map<String, FileUploadResponse> getUploadInfo(List<FileUploadRequest> fileUploadRequestList) {
        Map<String, FileUploadResponse> responseMap = new HashMap<>();
        String dataPath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        for (FileUploadRequest request : fileUploadRequestList) {
            responseMap.put(request.getOriginalFilename(), new FileUploadResponse(getFileStorageUrl(request,
                    dataPath)));
        }
        return responseMap;
    }

    // ===================================================================

    // 文件在OSS的存储位置
    private String getFileStorageUrl(FileUploadRequest fileUploadRequest, String dataPath) {
        String originalFilename = fileUploadRequest.getOriginalFilename();
        String fileKey = UUID.randomUUID().toString();
        String ext;
        if (originalFilename != null) {
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        } else {
            throw new ComelyMusicException(ResultCode.FILE_NAME_ERROR);
        }
        String type = getFileType(ext);
        return type + dataPath + "/" + fileKey + ext;
    }

//    public FileEntity upload(MultipartFile multipartFile) {
//        // 创建OSSClient实例。
////        OSS ossClient = MyFileConfig.getOssClient();
//
//        try {
//            InputStream inputStream = multipartFile.getInputStream();
//            // 根据日期构建目录
//            FileEntity file = getFileStorageUrl(multipartFile);
//            //文件上传OSS
//            ossClient.putObject(bucketName, file.getStorageUrl(), inputStream);
//            file.setVisitUrl("https://" + bucketName + "." + endpoint + "/" + file.getFileKey());
//            // 文件信息存入数据库
//            fileMapper.insert(file);
//            return file;
//        } catch (OSSException oe) {
//            log.error("Caught an OSSException, which means your request made it to OSS, but was rejected with an error response for some reason." +
//                    "\nError Message:" + oe.getErrorMessage() + "\nRequest ID:" + oe.getRequestId() + "\nHost ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            log.error("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network." + "\nError Message:" + ce.getMessage());
//        } catch (IOException e) {
//            log.error("获取文件流失败！");
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//        return null;
//    }

//    public FileDownloadContentDto download(String storageUrl) {
//        // 创建OSSClient实例。
////        OSS ossClient = MyFileConfig.getOssClient();
//
//        try {
//            // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
//            OSSObject ossObject = ossClient.getObject(bucketName, storageUrl);
//            // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
//            InputStream content = ossObject.getObjectContent();
//            byte[] contentBytes = MyFileUtil.inputStream2Bytes(content);
//            FileDownloadContentDto result = new FileDownloadContentDto();
//            result.setFileContent(contentBytes);
//            FileEntity fileEntity = getFileEntityByStorageUrl(storageUrl);
//            result.setFileEntityInfo(fileEntity);
//            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
//            content.close();
//            return result;
//        } catch (OSSException oe) {
//            log.error("Caught an OSSException, which means your request made it to OSS, but was rejected with an error response for some reason." +
//                    "\nError Message:" + oe.getErrorMessage() + "\nRequest ID:" + oe.getRequestId() + "\nHost ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            log.error("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network." + "\nError Message:" + ce.getMessage());
//        } catch (IOException e) {
//            log.error("获取文件流失败！");
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//        return null;
//    }
//
//    private FileEntity getFileEntityByStorageUrl(String storageUrl) {
//        QueryWrapper<FileEntity> wrapper = new QueryWrapper<>();
//        wrapper.eq("storage_url", storageUrl);
//        return fileMapper.selectOne(wrapper);
//    }

//    private FileEntity getFileStorageInfo(MultipartFile multipartFile) {
//        String dataPath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
//
//        String originalFilename = multipartFile.getOriginalFilename();
//        String ext;
//        if (originalFilename != null) {
//            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
//        } else {
//            throw new ComelyMusicException(ResultCode.FILE_NAME_ERROR);
//        }
//        String fileKey = UUID.randomUUID().toString();
//        String type = getFileType(ext);
//        String storageUrl = type + dataPath + "/" + fileKey + ext;
//        return new FileEntity(null, originalFilename, fileKey, ext,
//                multipartFile.getSize(), type, storageUrl, null);
//    }

    private String getFileType(String ext) {
        if (".jpg".equals(ext) || ".png".equals(ext)) {
            return FileType.IMAGE.toString();
        } else if (".mp3".equals(ext)) {
            return FileType.AUDIO.toString();
        } else if (".lyric".equals(ext)) {
            return FileType.LYRIC.toString();
        } else {
            throw new ComelyMusicException(ResultCode.FILE_TYPE_NOT_SUPPORTED_ERROR);
        }
    }
}
