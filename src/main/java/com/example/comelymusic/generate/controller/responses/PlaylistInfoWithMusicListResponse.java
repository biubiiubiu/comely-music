package com.example.comelymusic.generate.controller.responses;

import lombok.Data;

import java.util.List;

/**
 * description: 歌单+音乐列表
 *
 * @author: zhangtian
 * @since: 2022-05-14 11:39
 */
@Data
public class PlaylistInfoWithMusicListResponse {
    private UserPlaylistsSelectResponse.PlaylistInfo playlistInfo;
    private List<MusicSelectResponse.MusicInfo> musicInfoList;
}
