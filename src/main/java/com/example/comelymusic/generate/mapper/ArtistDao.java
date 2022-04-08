package com.example.comelymusic.generate.mapper;

import com.example.comelymusic.generate.entity.Artist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 歌手表 Mapper 接口
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Mapper
public interface ArtistDao extends BaseMapper<Artist> {

}
