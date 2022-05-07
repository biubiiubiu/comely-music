package com.example.comelymusic.generate.controller.requests;

import lombok.Data;

import java.util.List;

/**
 * description: 歌单-歌曲关联表创建约束
 *
 * @author: zhangtian
 * @since: 2022-05-07 11:51
 */
@Data
public class PlaylistMusicAddRequest {
    /**
     * 用户名与歌单名唯一确定一个歌单
     */
    private String username;
    private String playlistName;
    /**
     * 需要添加的歌曲名与歌手
     */
    private List<MusicAddInfo> musicAddInfoList;

    @Data
    public static class MusicAddInfo {
        /**
         * 歌曲名与歌手可以唯一确定一首Music
         */
        private String title;
        private String artistName;
    }
}
