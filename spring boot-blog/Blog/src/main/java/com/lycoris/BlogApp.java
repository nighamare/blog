package com.lycoris;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 14:59
 */
@MapperScan("com.lycoris.mapper")
@SpringBootApplication
public class BlogApp {
    public static void main(String[] args) {
        SpringApplication.run(BlogApp.class, args);
    }

}
