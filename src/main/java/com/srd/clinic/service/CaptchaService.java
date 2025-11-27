package com.srd.clinic.service;

import com.srd.clinic.dto.CaptchaVerifyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
@RequiredArgsConstructor
public class CaptchaService {

    @Value("${captcha.secret}")
    private String secret;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean verify(String token) {

        if (token == null || token.isBlank())
            return false;

        String url = "https://www.google.com/recaptcha/api/siteverify?secret=" + secret + "&response=" + token;

        ResponseEntity<CaptchaVerifyResponse> resp =
                restTemplate.postForEntity(url, null, CaptchaVerifyResponse.class);

        CaptchaVerifyResponse body = resp.getBody();

        if (body == null)
            return false;

        return body.isSuccess() && body.getScore() >= 0.5;
    }
}
