package com.example.comelymusic.generate.service.impl;

import com.example.comelymusic.generate.entity.File;
import com.example.comelymusic.generate.mapper.FileDao;
import com.example.comelymusic.generate.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Service
public class FileServiceImp extends ServiceImpl<FileDao, File> implements FileService {

}
