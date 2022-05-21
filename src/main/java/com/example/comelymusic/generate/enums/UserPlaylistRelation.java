package com.example.comelymusic.generate.enums;

import lombok.Data;

/**
 * description: 用户歌单关系
 *
 * @author: zhangtian
 * @since: 2022-05-12 11:54
 */
public enum UserPlaylistRelation {
    MY_LIKE(0),
    CREATE(1),
    COLLECT(2),
    RECENTLY_PLAY(3);

    UserPlaylistRelation(Integer relation) {
        this.relation = relation;
    }

    private Integer relation;

    public Integer getRelation() {
        return relation;
    }
}
