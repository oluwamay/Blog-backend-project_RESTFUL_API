package com.invogue_fashionblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InVogueFashionBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(InVogueFashionBlogApplication.class, args);
    }

}
