package com.jenkins.file.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author JenkinsZhang
 * @date 2020/8/5
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Value("${file.dest}")
    private String FILE_DEST;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/f/**").addResourceLocations("file:"+FILE_DEST);
    }
}
