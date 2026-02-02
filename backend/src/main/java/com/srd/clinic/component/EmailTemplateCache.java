package com.srd.clinic.component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Getter
@Component
public class EmailTemplateCache {
    
    private String cachedEmailTemplate;

    @PostConstruct
    public void load() {
        try (InputStream inputStream = getClass().getResourceAsStream("/templates/AppointmentEmailTemplate.html")) {
            if (inputStream == null) {
                throw new RuntimeException("Email template not found");
            }
            cachedEmailTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template", e);
        }
    }
}
