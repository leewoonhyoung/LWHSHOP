package com.shop.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")//application-properties 에 설정한 uploadPath 프로퍼티 값을 읽어옵니다.
    String uploadPath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }
}
