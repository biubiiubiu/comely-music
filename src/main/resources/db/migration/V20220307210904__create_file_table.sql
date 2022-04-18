CREATE TABLE file
(
    `id`           VARCHAR(32)      NOT NULL
        PRIMARY KEY COMMENT '文件ID',
    `name`         VARCHAR(64)      NOT NULL COMMENT '文件名,test.jpg',
    `file_key`     VARCHAR(64)      NOT NULL COMMENT '文件hash值，即ObjectKey',
    `size`         BIGINT DEFAULT 0 NOT NULL COMMENT '文件大小；单位byte',
    `type`         VARCHAR(32)      NULL COMMENT '文件类型，MP3-音频，IMAGE-图片，LYRIC-歌词',
    `storage_url`  VARCHAR(128)     NOT NULL COMMENT '文件在OSS中存储位置，用于oss增删改查',
    `visit_url`    VARCHAR(128)     NOT NULL COMMENT '文件公网访问url',
    `created_time` datetime(6)      NOT NULL COMMENT '创建时间',
    `updated_time` datetime(6)      NOT NULL COMMENT '更新时间',
    CONSTRAINT `file_file_key`
        UNIQUE (`file_key`),
    CONSTRAINT `file_storage_url`
        UNIQUE (`storage_url`),
    CONSTRAINT `file_visit_url`
        UNIQUE (`visit_url`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT '文件表';