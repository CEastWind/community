package com.liudl.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动项目的类，springboot内置tomcat，不需要手动再放到tomcat中运行
 */
@SpringBootApplication
@MapperScan("com.liudl.community.mapper")
public class CommunityApplication {

    public static void main(String[] args) {
            SpringApplication.run(CommunityApplication.class, args);
    }
}
