CREATE TABLE artist
(
    `id`                VARCHAR(32)                 NOT NULL
        PRIMARY KEY COMMENT '歌手ID',
    `name`              VARCHAR(64)                 NOT NULL COMMENT '歌手名字',
    `remark`            VARCHAR(128)                NULL COMMENT '歌手备注',
    `photo_id`          VARCHAR(32)                 NULL COMMENT '歌手照片ID',
    `fans_num`          INT         DEFAULT 0       NOT NULL COMMENT '粉丝数',
    `status`            VARCHAR(32) DEFAULT 'DRAFT' NOT NULL COMMENT '歌手上架状态，DRAFT-草稿，PUBLISHED-已上架，BLOCKED-已封禁',
    `recommend_factor`  INT         DEFAULT 0       NOT NULL COMMENT '推荐因数：越高越在上面',
    `works_playlist_id` varchar(32)                 NULL COMMENT '作品歌单id，唯一',
    `created_time`      datetime(6)                 NOT NULL COMMENT '创建时间',
    `updated_time`      datetime(6)                 NOT NULL COMMENT '更新时间',
    CONSTRAINT `artist_photo_id`
        FOREIGN KEY (`photo_id`) REFERENCES `file` (id),
    CONSTRAINT `artist_works_playlist_id`
        FOREIGN KEY (`works_playlist_id`) REFERENCES `playlist` (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT '歌手表';