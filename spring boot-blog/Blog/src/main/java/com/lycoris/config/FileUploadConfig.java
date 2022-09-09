package com.lycoris.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author tsh
 * @version 1.0
 * @date 2022/7/23 16:24
 */
@Configuration
public class FileUploadConfig implements WebMvcConfigurer {
    @Value("${file-upload.upload-path}")
    private String uploadPath;
    @Value("${file-upload.static-access-path}")
    private String staticAccessPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadPath);
    }
}
