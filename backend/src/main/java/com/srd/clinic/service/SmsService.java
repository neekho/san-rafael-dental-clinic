package com.srd.clinic.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
@Slf4j
public class SmsService {

    @Value("${sms.provider:semaphore}")
    private String provider;

    @Value("${semaphore.apiKey:}")
    private String semaphoreApiKey;

    // sends basic SMS via Semaphore API
    public void sendSms(String to, String message) {
        if ("semaphore".equalsIgnoreCase(provider)) {
            if (semaphoreApiKey == null || semaphoreApiKey.isBlank()) {
                log.warn("Semaphore API key not configured; skipping SMS");
                return;
            }
            String url = "https://api.semaphore.co/api/v4/messages";
            Map<String, String> body = Map.of(
                    "apikey", semaphoreApiKey,
                    "number", to,
                    "message", message
            );
            new RestTemplate().postForEntity(url, body, String.class);
        } else {
            log.warn("Unknown SMS provider '{}', skipping SMS", provider);
        }
    }
}
