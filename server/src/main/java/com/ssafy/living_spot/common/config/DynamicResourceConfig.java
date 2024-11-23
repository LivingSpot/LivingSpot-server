package com.ssafy.living_spot.common.config;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Configuration
public class DynamicResourceConfig implements WebMvcConfigurer {

    @Value("${file.dir}")
    private String fileDir;


    @Bean
    public ResourceHttpRequestHandler resourceHttpRequestHandler() throws Exception {
        ResourceHttpRequestHandler handler = new ResourceHttpRequestHandler();
        List<Resource> locations = Collections.singletonList(new FileSystemResource(fileDir));
        handler.setLocations(locations);
        return handler;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/profile/**")
                .addResourceLocations("file:" + fileDir + "/");
    }
}
