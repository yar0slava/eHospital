package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/users").setViewName("/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        CacheControl cacheControl = CacheControl.maxAge(7, TimeUnit.DAYS).noTransform().cachePrivate();
        registry.addResourceHandler("/view/")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(cacheControl);
    }

    /* Added CORS support for the application
     *
     * @param registry - CorsRegistry registry of the Spring
     */

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("token", "content-type")
                .allowCredentials(false).maxAge(4800);
    }

}
