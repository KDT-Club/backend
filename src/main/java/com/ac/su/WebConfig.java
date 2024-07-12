package com.ac.su;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // "/uploads/" URL 요청이 "file:uploads/" 로 매핑되도록 설정
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
