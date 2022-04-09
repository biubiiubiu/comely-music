package com.example.comelymusic.generate.mapper;

import com.example.comelymusic.generate.entity.FansArtist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 粉丝歌手关联表 Mapper 接口
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Mapper
@Component
public interface FansArtistMapper extends BaseMapper<FansArtist> {

}
