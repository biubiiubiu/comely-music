package com.example.comelymusic.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.comelymusic.generate.controller.requests.EntityTagCreateRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistSelectRequest;
import com.example.comelymusic.generate.entity.Artist;
import com.example.comelymusic.generate.entity.EntityTag;
import com.example.comelymusic.generate.entity.Music;
import com.example.comelymusic.generate.entity.Playlist;
import com.example.comelymusic.generate.enums.TagType;
import com.example.comelymusic.generate.mapper.EntityTagMapper;
import com.example.comelymusic.generate.service.ArtistService;
import com.example.comelymusic.generate.service.EntityTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.comelymusic.generate.service.MusicService;
import com.example.comelymusic.generate.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author zhangtian
 * @since 2022-05-07
 */
@Service
public class EntityTagServiceImpl extends ServiceImpl<EntityTagMapper, EntityTag> implements EntityTagService {
    @Autowired
    private EntityTagMapper tagMapper;

    @Autowired
    @Lazy
    private MusicService musicService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private PlaylistService playlistService;

    /**
     * 创建对应实体的标签，返回创建成功的数量
     */
    @Override
    public int create(EntityTagCreateRequest request) {
        List<String> entityIds = getIdByEntityName(request.getEntityName(), request.getType(), request.getUsername());
        int successTotal = 0;
        for (String id : entityIds) {
            //判断是否已经由entity_id--tag_name映射了，有的话应该避免重复
            QueryWrapper<EntityTag> wrapper = new QueryWrapper<>();
            wrapper.eq("entity_id", id);
            wrapper.eq("tag_name", request.getTagName());
            EntityTag existEntityTag = tagMapper.selectOne(wrapper);
            if (existEntityTag == null) {
                EntityTag tag = new EntityTag();
                tag.setEntityId(id)
                        .setTagName(request.getTagName())
                        .setEntityType(request.getType().toString());
                successTotal += tagMapper.insert(tag);
            }
        }
        return successTotal;
    }

    @Override
    public List<String> selectEntityIdsByTag(String tag) {
        QueryWrapper<EntityTag> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_name", tag);
        List<EntityTag> entityTags = tagMapper.selectList(wrapper);
        return entityTags.stream().map(EntityTag::getEntityId).collect(Collectors.toList());
    }

    private List<String> getIdByEntityName(String entityName, TagType type, String username) {
        List<String> idList = new ArrayList<>();
        if (TagType.MUSIC.toString().equals(type.toString())) {
            List<Music> musics = musicService.selectByName(entityName);
            idList = musics.stream().map(Music::getId).collect(Collectors.toList());
        } else if (TagType.ARTIST.toString().equals(type.toString())) {
            Artist artist = artistService.selectByArtistName(entityName);
            idList.add(artist.getId());
        } else if (TagType.PLAYLIST.toString().equals(type.toString())) {
            if (username != null && username.length() != 0) {
                PlaylistSelectRequest playlistSelectRequest = new PlaylistSelectRequest().setPlaylistName(entityName).setUsername(username);
                Playlist playlist = playlistService.selectPlaylist(playlistSelectRequest);
                idList.add(playlist.getId());
            }
        }
        return idList;
    }
}
