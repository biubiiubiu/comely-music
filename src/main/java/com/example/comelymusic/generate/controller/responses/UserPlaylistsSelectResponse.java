package com.example.comelymusic.generate.controller.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * description: 查询某个用户的歌单
 *
 * @author: zhangtian
 * @since: 2022-05-12 20:47
 */
@Data
public class UserPlaylistsSelectResponse {
    List<PlaylistModel> playlistModelList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlaylistModel {
        private String name;
        private Integer musicNum;
        private Integer visibility;
        private String createdUserNickname;
        private String description;
    }
}
