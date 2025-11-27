package com.srd.clinic.service;

import com.srd.clinic.dto.AppointmentRequest;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private EmailService emailService;

    @Value("${clinic.notification.email:}")
    private String clinicEmail;

    @Value("${clinic.notification.smsNumber:}")
    private String clinicSmsNumber;

    public void process(AppointmentRequest req) throws MessagingException {

       boolean validCaptcha = captchaService.verify(req.getCaptchaToken());
       
       if (!validCaptcha)
           throw new RuntimeException("Invalid CAPTCHA");


        if (clinicEmail == null || clinicEmail.isBlank())
            throw new RuntimeException("Clinic email is not configured (clinic.notification.email)");

        emailService.sendAppointmentEmailHtml(req, clinicEmail);

    }
}
