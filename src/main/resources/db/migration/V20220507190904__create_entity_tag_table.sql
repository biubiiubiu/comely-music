CREATE TABLE entity_tag
(
    `id`           VARCHAR(32) NOT NULL
        PRIMARY KEY COMMENT '标签ID',
    `entity_id`    VARCHAR(32) NOT NULL COMMENT '实体id，例：music_id playlist_id',
    `tag_name`     VARCHAR(64) NOT NULL COMMENT '标签名称，例：古风',
    `entity_type`  VARCHAR(32) NOT NULL COMMENT '实体类型，MUSIC PLAYLIST ARTIST',
    `created_time` datetime(6) NOT NULL COMMENT '创建时间',
    `updated_time` datetime(6) NOT NULL COMMENT '更新时间',
    CONSTRAINT `tag_entity_id`
        UNIQUE (`entity_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT '文件表';