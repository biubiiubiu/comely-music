package com.example.comelymusic.generate.service.impl;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.comelymusic.generate.common.ComelyMusicException;
import com.example.comelymusic.generate.common.config.OssTokenConfig;
import com.example.comelymusic.generate.common.utils.RedisUtils;
import com.example.comelymusic.generate.controller.requests.file.FileCommonRequest;
import com.example.comelymusic.generate.controller.requests.file.FileUploadRequest;
import com.example.comelymusic.generate.controller.responses.file.FileUploadResponse;
import com.example.comelymusic.generate.controller.responses.file.OssTokenInfo;
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
    private final static String ENDPOINT = "oss-cn-huhehaote.aliyuncs.com";
    private final static String BUCKET_NAME = "comely-music-bucket";
    /**
     * 存储到redis的sts-token的key前缀，加上用户名就可以组成key
     */
    private final static String STS_TOKEN_KEY_PREFIX = "sts-token-";

    @Autowired
    FileMapper fileMapper;

//    @Autowired
//    OSS ossClient;

    @Autowired
    AssumeRoleResponse roleResponse;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 客户端请求上传文件，生成并返回需要上传的文件信息
     *
     * @param fileUploadRequest 包含用户名，和单个或多个文件信息
     * @return Map(originalFilename, storageUrl)
     */
    @Override
    public FileUploadResponse getUploadInfo(FileUploadRequest fileUploadRequest) {
        FileUploadResponse responseMap = new FileUploadResponse();
        OssTokenInfo ossToken = getOssToken(fileUploadRequest.getUsername());
        responseMap.setOssTokenInfo(ossToken);
        // 多文件一次批量上传时间设置为同一天
        String dataPath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        Map<String, String> fileStorageUrlMap = new HashMap<>();
        for (FileUploadRequest.FileUploadInfo request : fileUploadRequest.getFileUploadInfoList()) {
            String storageUrl = getFileStorageUrl(request, dataPath);
            fileStorageUrlMap.put(request.getOriginalFilename(), storageUrl);
        }
        responseMap.setFileStorageUrlMap(fileStorageUrlMap);
        return responseMap;
    }

    /**
     * 保存成功上传的文件信息
     *
     * @param request 成功上传文件的基本信息
     * @return 全部上传成功
     */
    @Override
    public Boolean saveUploadInfo(FileCommonRequest request) {
        Map<String, FileCommonRequest.CommonInfo> fileKeyInfoMap = request.getFileKeyInfoMap();
        int num = 0;
        int total = fileKeyInfoMap.size();
        for (String key : fileKeyInfoMap.keySet()) {
            // 防止插入重复的file_key导致SQLEXCEPTION
            QueryWrapper<FileEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("file_key", key);
            FileEntity entity = fileMapper.selectOne(wrapper);
            if (entity != null) {
                fileKeyInfoMap.remove(key);
                total--;
            }
        }
        for (Map.Entry<String, FileCommonRequest.CommonInfo> entry : fileKeyInfoMap.entrySet()) {
            num += fileMapper.insert(commonRequest2Entity(entry.getKey(), entry.getValue()));
        }
        return num == total;
    }

    /**
     * 获取oss-token
     *
     * @param username 用户名
     * @return OssTokenInfo
     */
    @Override
    public OssTokenInfo getOssToken(String username) {
        // 先检查redis有没有，没有的话新建一个token
        String key = STS_TOKEN_KEY_PREFIX + username;
        OssTokenInfo ossToken = (OssTokenInfo) redisUtils.getObject(key, OssTokenInfo.class);
        if (ossToken != null) {
            return ossToken;
        } else {
            OssTokenInfo newOssToken = new OssTokenInfo();
            String requestId = roleResponse.getRequestId();
            String accessKeyId = roleResponse.getCredentials().getAccessKeyId();
            String accessKeySecret = roleResponse.getCredentials().getAccessKeySecret();
            String securityToken = roleResponse.getCredentials().getSecurityToken();
            String expiration = roleResponse.getCredentials().getExpiration();
            newOssToken.setRequestId(requestId)
                    .setEndpoint(ENDPOINT)
                    .setBucketName(BUCKET_NAME)
                    .setAccessKeyId(accessKeyId)
                    .setAccessKeySecret(accessKeySecret)
                    .setSecurityToken(securityToken)
                    .setExpiration(expiration);
            // oss-token最大过期时间是3600L
            redisUtils.setObject(key, newOssToken, OssTokenConfig.EFFECTIVE_TIME);
            return newOssToken;
        }
    }

    // ===================================================================

    // 文件在OSS的存储位置
    private String getFileStorageUrl(FileUploadRequest.FileUploadInfo fileUploadInfo, String dataPath) {
        String originalFilename = fileUploadInfo.getOriginalFilename();
        String fileKey = UUID.randomUUID().toString();
        String type = getFileType(originalFilename);
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        return type + "/" + dataPath + "/" + fileKey + ext;
    }

    private String getFileType(String originalFilename) {
        String ext;
        if (originalFilename != null) {
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            switch (ext) {
                case ".jpg":
                case ".png":
                    return FileType.IMAGE.toString();
                case ".mp3":
                    return FileType.AUDIO.toString();
                case ".lyric":
                    return FileType.LYRIC.toString();
                default:
                    throw new ComelyMusicException(ResultCode.FILE_TYPE_NOT_SUPPORTED_ERROR);
            }
        } else {
            throw new ComelyMusicException(ResultCode.FILE_NAME_ERROR);
        }
    }

    private FileEntity commonRequest2Entity(String fileKey, FileCommonRequest.CommonInfo commonInfo) {
        FileEntity entity = new FileEntity();
        entity.setName(commonInfo.getFilename());
        entity.setFileKey(fileKey);
        entity.setSize(commonInfo.getSize());
        entity.setType(getFileType(commonInfo.getFilename()));
        entity.setStorageUrl(commonInfo.getStorageUrl());
        entity.setVisitUrl(getVisitUrlByKey(fileKey));
        return entity;
    }

    private String getVisitUrlByKey(String fileKey) {
        return "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + fileKey;
    }
}
