package com.example.comelymusic.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.comelymusic.generate.controller.requests.ArtistCreateRequest;
import com.example.comelymusic.generate.entity.Artist;
import com.example.comelymusic.generate.enums.ArtistStatus;
import com.example.comelymusic.generate.mapper.ArtistMapper;
import com.example.comelymusic.generate.service.ArtistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌手表 服务实现类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Service
public class ArtistServiceImpl extends ServiceImpl<ArtistMapper, Artist> implements ArtistService {
    @Autowired
    private ArtistMapper mapper;

    private final static String DEFAULT_REMARK = "此音乐人暂无介绍";

    /**
     * 创建音乐人，一次只能创建一个，就像用户一样
     *
     * @param request 创建要求
     * @return 创建结果，为0失败
     */
    @Override
    public int create(ArtistCreateRequest request) {
        Artist artist = request2Artist(request);
        QueryWrapper<Artist> wrapper = new QueryWrapper<>();
        wrapper.eq("name", request.getArtistName());
        Artist artist1 = mapper.selectOne(wrapper);
        if (artist1 == null) {
            return mapper.insert(artist);
        }
        return 0;
    }

    /**
     * 根据名字查询artist
     *
     * @param artistName 名字
     * @return artist
     */
    @Override
    public Artist selectByArtistName(String artistName) {
        QueryWrapper<Artist> wrapper = new QueryWrapper<>();
        wrapper.eq("name", artistName);
        return mapper.selectOne(wrapper);
    }

    private Artist request2Artist(ArtistCreateRequest request) {
        Artist artist = new Artist();
        artist.setName(request.getArtistName());
        artist.setRemark(request.getRemark() != null ? request.getRemark() : DEFAULT_REMARK);
        artist.setPhotoId(request.getPhotoId() != null ? request.getPhotoId() : null);
        artist.setFansNum(request.getFansNum() != null ? request.getFansNum() : null);
        String status = request.getStatus();
        if (ArtistStatus.DRAFT.toString().equals(status)
                || ArtistStatus.BLOCKED.toString().equals(status)
                || ArtistStatus.PUBLISHED.toString().equals(status)) {
            artist.setStatus(status);
        } else {
            artist.setStatus(ArtistStatus.DRAFT.toString());
        }
        artist.setRecommendFactor(request.getRecommendFactor() != null ? request.getRecommendFactor() : null);
        artist.setWorksPlaylistId(request.getWorksPlaylist_id() != null ? request.getWorksPlaylist_id() : null);
        // 其它默认参数后续可以加上，例如默认头像等
        return artist;
    }
}
