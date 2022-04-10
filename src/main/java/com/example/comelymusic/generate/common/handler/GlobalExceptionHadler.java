package com.example.comelymusic.generate.common.handler;

import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.common.ComelyMusicException;
import com.example.comelymusic.generate.enums.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

/**
 * description: 全局异常处理
 *
 * @author: zhangtian
 * @since: 2022-04-09 13:01
 */
public class GlobalExceptionHadler {
    /**-------- 通用异常处理方法 --------**/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error();    // 通用异常结果
    }

    /**-------- 指定异常处理方法 --------**/
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public R error(NullPointerException e) {
        e.printStackTrace();
        return R.setResult(ResultCode.NULL_POINT);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public R error(IndexOutOfBoundsException e) {
        e.printStackTrace();
        return R.setResult(ResultCode.HTTP_CLIENT_ERROR);
    }

    /**-------- 自定义定异常处理方法 --------**/
    @ExceptionHandler(ComelyMusicException.class)
    @ResponseBody
    public R error(ComelyMusicException e) {
        e.printStackTrace();
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
