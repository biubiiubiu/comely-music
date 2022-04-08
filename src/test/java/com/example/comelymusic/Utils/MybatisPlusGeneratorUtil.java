package com.example.comelymusic.Utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import org.junit.jupiter.api.Test;

/**
 * description: mybatis-plus代码生成器
 *
 * @author: zhangtian
 * @since: 2022-04-08 18:48
 */
public class MybatisPlusGeneratorUtil {

    @Test
    void codeGenerate() {
        String targetPath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\example\\comelymusic";
        FastAutoGenerator.create("jdbc:mysql://39.99.112.72:3306/comely-music?characterEncoding=UTF8&userSSL=false", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("zhangtian") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(targetPath); // 指定输出目录
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user"); // 设置需要生成的表名
                })
                .execute();

    }
}
