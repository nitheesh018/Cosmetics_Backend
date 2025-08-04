package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // ✅ Allow frontend origin
        config.setAllowCredentials(true);  // allow cookies and Authorization headers
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));  // your React frontend

        // ✅ Allow specific headers (including Authorization for JWT)
        config.setAllowedHeaders(Arrays.asList(
                "Origin",
                "Content-Type",
                "Accept",
                "Authorization",
                "X-Requested-With"
        ));

        // ✅ Allow HTTP methods for your API
        config.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        // ✅ Expose Authorization header so frontend can read it (optional)
        config.setExposedHeaders(Arrays.asList("Authorization"));

        // ✅ Apply config to all URLs
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
