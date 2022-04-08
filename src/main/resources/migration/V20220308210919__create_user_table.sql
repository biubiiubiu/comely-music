CREATE TABLE user
(
    `id`           VARCHAR(32)       NOT NULL
        PRIMARY KEY COMMENT '用户ID',
    `username`     VARCHAR(64)       NOT NULL COMMENT '用户名',
    `nickname`     VARCHAR(64)       NULL COMMENT '用户昵称',
    `password`     VARCHAR(64)       NOT NULL COMMENT '加密后的密码',
    `gender`       VARCHAR(255)      NULL COMMENT '性别',
    `avatar_id`    VARCHAR(32)       NULL COMMENT '头像文件ID',
    `role`         tinyint DEFAULT 0 NOT NULL COMMENT '用户身份，0-用户，1-管理员，2-歌手',
    `created_time` datetime(6)       NOT NULL COMMENT '创建时间',
    `updated_time` datetime(6)       NOT NULL COMMENT '更新时间',
    CONSTRAINT `user_username`
        UNIQUE (`username`),
    CONSTRAINT `user_avatar_id`
        FOREIGN KEY (`avatar_id`) REFERENCES `file` (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT '用户表';