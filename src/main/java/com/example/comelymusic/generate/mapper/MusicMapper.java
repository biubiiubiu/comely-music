package com.example.comelymusic.generate.mapper;

import com.example.comelymusic.generate.entity.Music;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 歌曲表 Mapper 接口
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Mapper
@Component
public interface MusicMapper extends BaseMapper<Music> {

}
