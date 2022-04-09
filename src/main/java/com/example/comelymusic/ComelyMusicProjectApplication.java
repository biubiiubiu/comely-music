package com.example.comelymusic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.comelymusic.generate.mapper")
public class ComelyMusicProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComelyMusicProjectApplication.class, args);
    }

}
