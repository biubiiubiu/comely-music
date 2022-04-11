package com.example.comelymusic.generate.service.impl;

import com.aliyun.oss.*;
import com.aliyun.oss.model.OSSObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.comelymusic.generate.common.ComelyMusicException;
import com.example.comelymusic.generate.common.config.MyFileConfig;
import com.example.comelymusic.generate.common.utils.MyFileUtil;
import com.example.comelymusic.generate.dto.FileDownloadContentDto;
import com.example.comelymusic.generate.entity.FileEntity;
import com.example.comelymusic.generate.enums.FileType;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.mapper.FileMapper;
import com.example.comelymusic.generate.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
    private static final String endpoint = "oss-cn-huhehaote.aliyuncs.com";
    private final String bucketName = "comely-music-bucket";

    @Autowired
    FileMapper fileMapper;

    @Autowired
    OSS ossClient;

    /**
     * 上传文件
     *
     * @param multipartFile 从前端获取到的文件对象
     * @return 文件资源信息，包括文件名、上传成功的文件路径（可以直接访问）等
     */
    @Override
    public FileEntity uploadFile(MultipartFile multipartFile) {
        return upload(multipartFile);
    }

    /**
     * 下载文件
     *
     * @param storageUrl 文件在OSS服务器存储位置
     * @return 文件内容
     */
    @Override
    public FileDownloadContentDto downloadFile(String storageUrl) {
        return download(storageUrl);
    }

    // ===================================================================

    public FileEntity upload(MultipartFile multipartFile) {
        // 创建OSSClient实例。
//        OSS ossClient = MyFileConfig.getOssClient();

        try {
            InputStream inputStream = multipartFile.getInputStream();
            // 根据日期构建目录
            FileEntity file = getFileStorageInfo(multipartFile);
            //文件上传OSS
            ossClient.putObject(bucketName, file.getStorageUrl(), inputStream);
            file.setVisitUrl("https://" + bucketName + "." + endpoint + "/" + file.getFileKey());
            // 文件信息存入数据库
            fileMapper.insert(file);
            return file;
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

    public FileDownloadContentDto download(String storageUrl) {
        // 创建OSSClient实例。
//        OSS ossClient = MyFileConfig.getOssClient();

        try {
            // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
            OSSObject ossObject = ossClient.getObject(bucketName, storageUrl);
            // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
            InputStream content = ossObject.getObjectContent();
            byte[] contentBytes = MyFileUtil.inputStream2Bytes(content);
            FileDownloadContentDto result = new FileDownloadContentDto();
            result.setFileContent(contentBytes);
            FileEntity fileEntity = getFileEntityByStorageUrl(storageUrl);
            result.setFileEntityInfo(fileEntity);
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            content.close();
            return result;
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

    private FileEntity getFileEntityByStorageUrl(String storageUrl) {
        QueryWrapper<FileEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("storage_url", storageUrl);
        return fileMapper.selectOne(wrapper);
    }

    private FileEntity getFileStorageInfo(MultipartFile multipartFile) {
        String dataPath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        String originalFilename = multipartFile.getOriginalFilename();
        String ext;
        if (originalFilename != null) {
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        } else {
            throw new ComelyMusicException(ResultCode.FILE_NAME_ERROR);
        }
        String fileKey = UUID.randomUUID().toString();
        String type = getFileType(ext);
        String storageUrl = type + dataPath + "/" + fileKey + ext;
        return new FileEntity(null, originalFilename, fileKey, ext,
                (int) multipartFile.getSize(), type, storageUrl, null);
    }

    private String getFileType(String ext) {
        if (".jpg".equals(ext) || ".png".equals(ext)) {
            return FileType.IMAGE.toString();
        } else if (".mp3".equals(ext)) {
            return FileType.MP3.toString();
        } else if (".lyric".equals(ext)) {
            return FileType.LYRIC.toString();
        } else {
            throw new ComelyMusicException(ResultCode.FILE_TYPE_NOT_SUPPORTED_ERROR);
        }
    }
}
