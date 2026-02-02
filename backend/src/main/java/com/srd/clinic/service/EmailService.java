package com.srd.clinic.service;

import com.srd.clinic.component.EmailTemplateCache;
import com.srd.clinic.dto.AppointmentRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmailSender;

    private final EmailTemplateCache emailTemplateCache;

    @Async
    public CompletableFuture<Void> sendAppointmentEmailHtml(AppointmentRequest req, String[] to) {
        try {
            long start = System.currentTimeMillis();
            log.info("preparing appointment email asynchronously");

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            
            helper.setFrom(fromEmailSender);
            helper.setTo(to);
            helper.setSubject("New Appointment Request");
            
            // Format names to title case
            String formattedFirstName = formatToTitleCase(req.getFirstName());
            String formattedLastName = formatToTitleCase(req.getLastName());
            
            String html = emailTemplateCache.getCachedEmailTemplate()
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

            long sendStart = System.currentTimeMillis();
            mailSender.send(mimeMessage);
            log.info("SMTP send took {} ms", System.currentTimeMillis() - sendStart);
            log.info("EmailService completed in {} ms", System.currentTimeMillis() - start);
            
            return CompletableFuture.completedFuture(null);
        } catch (MessagingException e) {
            log.error("Failed to send appointment email", e);
            return CompletableFuture.failedFuture(e);
        }
    }

    private String formatToTitleCase(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }
        
        String trimmedName = name.trim();
        return trimmedName.substring(0, 1).toUpperCase() + 
               trimmedName.substring(1).toLowerCase();
    }
}
