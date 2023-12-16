package com.example.websocket.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/ws-chat")  // Endpoint WebSocket, który wymaga dostępu
                .allowedOrigins("http://localhost:4200")  // Adresy, z których można pochodzić (np. adres Angulara)
                .allowedMethods("*")  // Dozwolone metodys
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowCredentials(true);  // Pozwala na przesyłanie ciasteczek (jeśli jest wymagane)
    }
}
