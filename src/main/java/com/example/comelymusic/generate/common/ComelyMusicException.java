package com.example.comelymusic.generate.common;

import com.example.comelymusic.generate.enums.ResultCode;
import lombok.Data;

/**
 * description: 全局异常
 *
 * @author: zhangtian
 * @since: 2022-04-09 12:59
 */
@Data
public class ComelyMusicException extends RuntimeException {
    private Integer code;
    private ResultCode resultCode;

    public ComelyMusicException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ComelyMusicException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.resultCode = resultCode;
    }

    @Override
    public String toString() {
        return "ComelyMusicException{" + "code=" + code + ", message=" + this.getMessage() + '}';
    }
}
