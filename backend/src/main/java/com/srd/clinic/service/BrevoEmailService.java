package com.srd.clinic.service;

import com.srd.clinic.component.EmailTemplateCache;
import com.srd.clinic.dto.AppointmentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailReplyTo;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrevoEmailService {

    private final EmailTemplateCache emailTemplateCache;

    @Value("${brevo.apiKey}")
    private String brevoApiKey;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async("taskExecutor")
    public CompletableFuture<Void> sendAppointmentEmailHtml(AppointmentRequest req, String[] to) {
        try {
            long start = System.currentTimeMillis();
            log.info("Starting Brevo async email send for appointment from {} {}", req.getFirstName(), req.getLastName());

            ApiClient defaultClient = Configuration.getDefaultApiClient();
            ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
            apiKey.setApiKey(brevoApiKey);

            TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();

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

            // Create sender
            SendSmtpEmailSender sender = new SendSmtpEmailSender();
            sender.setName("San Rafael Dental Center");
            sender.setEmail(fromEmail);

            // Create recipients list
            List<SendSmtpEmailTo> toList = new ArrayList<>();
            for (String recipientEmail : to) {
                SendSmtpEmailTo recipient = new SendSmtpEmailTo();
                recipient.setEmail(recipientEmail);
                toList.add(recipient);
            }

            // Create reply-to
            SendSmtpEmailReplyTo replyTo = new SendSmtpEmailReplyTo();
            replyTo.setEmail(req.getEmail());

            // Create email
            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            sendSmtpEmail.setSender(sender);
            sendSmtpEmail.setTo(toList);
            sendSmtpEmail.setHtmlContent(html);
            sendSmtpEmail.setSubject("New Appointment Request");
            sendSmtpEmail.setReplyTo(replyTo);

            long sendStart = System.currentTimeMillis();
            
            // Send email via Brevo API
            apiInstance.sendTransacEmail(sendSmtpEmail);
            
            long emailDuration = System.currentTimeMillis() - sendStart;
            long totalDuration = System.currentTimeMillis() - start;
            
            log.info("Brevo email sent successfully! API call took {} ms, Total async task: {} ms", 
                     emailDuration, totalDuration);
            
            return CompletableFuture.completedFuture(null);
            
        } catch (Exception e) {
            log.error("Failed to send appointment email via Brevo to {}", String.join(",", to), e);
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