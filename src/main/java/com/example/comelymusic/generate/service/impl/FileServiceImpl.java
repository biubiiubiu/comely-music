package com.example.comelymusic.generate.service.impl;

import com.aliyun.oss.*;
import com.example.comelymusic.generate.common.ComelyMusicException;
import com.example.comelymusic.generate.common.config.MyFileConfig;
import com.example.comelymusic.generate.dto.FileSourceDto;
import com.example.comelymusic.generate.entity.FileEntity;
import com.example.comelymusic.generate.enums.FileType;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.mapper.FileMapper;
import com.example.comelymusic.generate.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
    private final String endpoint = MyFileConfig.getENDPOINT();
    private final String bucketName = MyFileConfig.getBucketName();

    @Override
    public FileSourceDto uploadFile(MultipartFile multipartFile) {
        return upload(multipartFile);
    }

    private FileSourceDto getFileStorageInfo(MultipartFile multipartFile) {
        String dataPath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        String originalFilename = multipartFile.getOriginalFilename();
        String filenameUUID = UUID.randomUUID().toString();
        String ext;
        if (originalFilename != null) {
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        } else {
            throw new ComelyMusicException(ResultCode.FILE_NAME_ERROR);
        }
        String type = getFileType(ext);
        String storageUrl = type + dataPath + "/" + filenameUUID + ext;
        return new FileSourceDto(originalFilename, filenameUUID, ext,
                (int) multipartFile.getSize(), type, storageUrl, null);
    }

    private String getFileType(String ext) {
        if ("jpg".equals(ext) || "png".equals(ext)) {
            return FileType.IMAGE.toString();
        } else if ("mp3".equals(ext)) {
            return FileType.MP3.toString();
        } else if ("lyric".equals(ext)) {
            return FileType.LYRIC.toString();
        } else {
            throw new ComelyMusicException(ResultCode.FILE_TYPE_NOT_SUPPORTED_ERROR);
        }
    }

    /**
     * 上传文件
     *
     * @param multipartFile 从前端获取到的文件对象
     * @return 文件资源信息，包括文件名、上传成功的文件路径（可以直接访问）等
     */
    public FileSourceDto upload(MultipartFile multipartFile) {
        // 创建OSSClient实例。
        OSS ossClient = MyFileConfig.getOssClient();

        try {
            InputStream inputStream = multipartFile.getInputStream();
            // 根据日期构建目录
            FileSourceDto storageInfo = getFileStorageInfo(multipartFile);
            //文件上传OSS
            ossClient.putObject(bucketName, storageInfo.getStorageUrl(), inputStream);
            storageInfo.setVisitUrl("https://" + bucketName + "." + endpoint + "/" + storageInfo.getFilenameUUID());
            return storageInfo;
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, but was rejected with an error response for some reason." +
                    "\nError Message:" + oe.getErrorMessage() + "\nRequest ID:" + oe.getRequestId() + "\nHost ID:" + oe.getHostId());
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network." + "\nError Message:" + ce.getMessage());
        } catch (IOException e) {
            log.error("获取文件流失败！");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}
