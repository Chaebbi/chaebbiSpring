package com.ae.chaebbiSpring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //모든 경로에 대해
        registry.addMapping("/**")
                //Orgin이 http:localhost:3000에 대해
                .allowedOriginPatterns("http://127.0.0.1:5500")
                .allowedOrigins("*")
                //GET, POST, PUT,  메소드를 허용한다.
                .allowedMethods("GET","HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(MAX_AGE_SECS);

    }
}
