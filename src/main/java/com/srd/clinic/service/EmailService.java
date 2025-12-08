package com.srd.clinic.service;

import com.srd.clinic.dto.AppointmentRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendAppointmentEmailHtml(AppointmentRequest req, String to) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        
        helper.setTo(to);
        helper.setSubject("New Appointment Request");
        String html = loadTemplate()
            .replace("{{fullName}}", req.getFirstName() + " " + req.getLastName())
            .replace("{{email}}", req.getEmail())
            .replace("{{mobile}}", req.getMobile())
            .replace("{{service}}", req.getService())
            .replace("{{preferredDate}}", req.getPreferredDate())
            .replace("{{preferredTime}}", req.getPreferredTime())
            .replace("{{notes}}",
                (req.getNotes() == null || req.getNotes().isBlank()) ? "None" : req.getNotes()
            );

        helper.setText(html, true);
        helper.setReplyTo(req.getEmail());

        mailSender.send(mimeMessage);
    }

    private String loadTemplate() {
        try {
            return new String(
                Files.readAllBytes(Paths.get("src/main/resources/templates/AppointmentEmailTemplate.html"))
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template", e);
        }
    }
}
