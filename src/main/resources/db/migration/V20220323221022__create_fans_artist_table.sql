CREATE TABLE fans_artist
(
    `user_id`   VARCHAR(32) NOT NULL COMMENT '用户ID（粉丝）',
    `artist_id` VARCHAR(32) NOT NULL COMMENT '歌手ID',
    CONSTRAINT `artist_music_artist_id`
        FOREIGN KEY (`artist_id`) REFERENCES `artist` (id),
    CONSTRAINT `artist_music_music_id`
        FOREIGN KEY (`user_id`) REFERENCES `music` (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '粉丝歌手关联表';