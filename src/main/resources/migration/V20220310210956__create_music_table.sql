CREATE TABLE music
(
    `id`           VARCHAR(32)                 NOT NULL
        PRIMARY KEY COMMENT '歌曲ID',
    `name`         VARCHAR(64)                 NOT NULL COMMENT '歌曲名',
    `description`  TEXT                        NULL COMMENT '歌曲简介',
    `artist_id`    VARCHAR(32)                 NULL COMMENT '歌手ID',
    `cover_id`     VARCHAR(32)                 NULL COMMENT '歌曲封面ID',
    `mp3_id`       VARCHAR(32)                 NULL COMMENT 'mp3文件ID',
    `lyric_id`     VARCHAR(32)                 NULL COMMENT '歌词文件ID',
    `status`       VARCHAR(32) DEFAULT 'DRAFT' NOT NULL COMMENT '歌曲上架状态，PUBLISHED-已上架，CLOSED-已下架',
    `created_time` datetime(6)                 NOT NULL COMMENT '创建时间',
    `updated_time` datetime(6)                 NOT NULL COMMENT '更新时间',
    CONSTRAINT `c_music_mp3_id`
        FOREIGN KEY (`mp3_id`) REFERENCES `file` (id),
    CONSTRAINT `c_music_lyric_id`
        FOREIGN KEY (`lyric_id`) REFERENCES `file` (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT '歌曲表';