package com.srd.clinic.service;

import com.srd.clinic.dto.AppointmentRequest;
import com.srd.clinic.exception.ConfigurationException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppointmentService {

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private BrevoEmailService brevoEmailService;

    @Value("${clinic.notification.email:}")
    private String[] clinicEmail;

    @Value("${clinic.notification.smsNumber:}")
    private String clinicSmsNumber;

    public void process(AppointmentRequest req) {

    long start = System.currentTimeMillis();

    long captchaStart = System.currentTimeMillis();
    boolean validCaptcha = captchaService.verify(req.getCaptchaToken());
    log.info("CAPTCHA Verification tooks {} ms ", System.currentTimeMillis() - captchaStart);
   
    if (!validCaptcha)
        throw new RuntimeException("Invalid CAPTCHA");

    if (clinicEmail == null || clinicEmail.length == 0 || (clinicEmail.length == 1 && (clinicEmail[0] == null || clinicEmail[0].isBlank())))
        throw new ConfigurationException("Clinic email is not configured (clinic.notification.email)");

    long emailStart = System.currentTimeMillis();
    brevoEmailService.sendAppointmentEmailHtml(req, clinicEmail);
    log.info("Email sending initiated (async) in {} ms ", System.currentTimeMillis() - emailStart);
    
    log.info("appointment processing completed in {} ms ", System.currentTimeMillis() - start);
    }
}
