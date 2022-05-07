package com.example.comelymusic.generate.controller.requests;

import lombok.Data;

/**
 * description: 歌单信息修改约束
 *
 * @author: zhangtian
 * @since: 2022-05-07 12:30
 */
@Data
public class PlaylistUpdateRequest {
    /**
     * 这两个为了确定是谁修改了歌单
     */
    private String oldName;
    private String oldUsername;

    /**
     * 这些是可修改项
     */
    private String newName;
    private String newUsername;
    private String coverId;
    private Integer musicNum;
    private String description;
    private String status;
    private Integer collectionNum;
}
