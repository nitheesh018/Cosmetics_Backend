package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve uploaded images from /images/** URL
        String absolutePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/images/**")
                .addResourceLocations(absolutePath);  // mapped to full path like file:///D:/cosmetics-backend/uploads/
    }
}
