package com.example.comelymusic.generate.enums;

import lombok.Getter;

/**
 * description: 统一结果枚举
 *
 * @author: zhangtian
 * @since: 2022-04-09 12:52
 */
@Getter
public enum ResultCode {
    /**
     * 通用异常
     */
    SUCCESS(true, 20000, "成功"),
    UNKNOWN_ERROR(false, 20001, "未知错误"),
    PARAM_ERROR(false, 20002, "参数错误"),
    NULL_POINT(false, 20003, "空指针异常"),
    HTTP_CLIENT_ERROR(false, 20004, "http client异常"),


    USERNAME_EXISTS(false, 30001, "用户名已存在!"),
    USER_NOT_EXIST(false, 30002, "用户不存在!"),
    USER_LOGIN_FAILED(false, 30003, "用户名或密码不正确！"),

    FILE_NAME_ERROR(false, 40001, "获取文件名异常!"),
    FILE_GET_STREAM_ERROR(false, 40001, "获取文件InputStream异常!"),
    FILE_TYPE_NOT_SUPPORTED_ERROR(false, 40001, "不支持的文件类型!"),

    ARTIST_CREATE_ERROR(false, 50001, "创建音乐人失败!"),

    MUSIC_CREATE_ERROR(false, 60001, "创建音乐失败!"),

    PLAYLIST_CREATE_ERROR(false, 70001, "创建音乐失败!"),
    PLAYLIST_DELETE_ERROR(false, 70002, "删除音乐失败!"),
    PLAYLIST_ADD_MUSIC_ERROR(false, 70003, "添加音乐到歌单失败!");


    // 响应是否成功
    private Boolean success;
    // 响应状态码
    private Integer code;
    // 响应信息
    private String message;

    ResultCode(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
