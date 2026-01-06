package com.srd.clinic.service;

import com.srd.clinic.dto.AppointmentRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmailSender;


    public void sendAppointmentEmailHtml(AppointmentRequest req, String[] to) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        
        helper.setFrom(fromEmailSender);
        helper.setTo(to);
        helper.setSubject("New Appointment Request");
        
        // Format names to title case
        String formattedFirstName = formatToTitleCase(req.getFirstName());
        String formattedLastName = formatToTitleCase(req.getLastName());
        
        String html = loadTemplate()
            .replace("{{fullName}}", formattedFirstName + " " + formattedLastName)
            .replace("{{email}}", req.getEmail())
            .replace("{{mobile}}", req.getMobile())
            .replace("{{service}}", req.getService())
            .replace("{{preferredDate}}", req.getPreferredDate())
            .replace("{{preferredTime}}", req.getPreferredTime())
            .replace("{{notes}}",
                (req.getNotes() == null || req.getNotes().isBlank()) ? "None" : req.getNotes()
            );

        helper.setText(html, true);
        helper.setReplyTo(new InternetAddress(req.getEmail()));

        mailSender.send(mimeMessage);
    }

    private String loadTemplate() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("templates/AppointmentEmailTemplate.html");
            if (inputStream == null) {
                throw new RuntimeException("Email template not found");
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template", e);
        }
    }

    /**
     * Formats a name to title case (first letter capitalized, rest lowercase)
     * Handles null and empty strings gracefully
     * 
     * @param name the name to format
     * @return formatted name in title case
     */
    private String formatToTitleCase(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }
        
        String trimmedName = name.trim();
        return trimmedName.substring(0, 1).toUpperCase() + 
               trimmedName.substring(1).toLowerCase();
    }
}
