package com.example.comelymusic.generate.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * description: 文件处理工具类
 *
 * @author: zhangtian
 * @since: 2022-04-11 16:35
 */
@Component
public class MyFileUtils {
    private final static int MAX_READ_LIMIT = 100;

    /**
     * 把InputStream转换成byte[]
     */
    public static byte[] inputStream2Bytes(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[MAX_READ_LIMIT];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, MAX_READ_LIMIT)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        return swapStream.toByteArray();
    }

    /**
     * 把byte[]转换成InputStream
     */
    public static InputStream bytes2InputStream(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }
}
