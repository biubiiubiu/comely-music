package com.example.comelymusic.generate.mapper;

import com.example.comelymusic.generate.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 文件表 Mapper 接口
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Mapper
@Component
public interface FileMapper extends BaseMapper<File> {

}
