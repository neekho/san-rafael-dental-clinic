package com.srd.clinic.controller;

import com.srd.clinic.dto.AppointmentRequest;
import com.srd.clinic.service.AppointmentService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @RateLimiter(name = "createAppointment")
    public ResponseEntity<?> createAppointment(@Valid @RequestBody AppointmentRequest request) throws MessagingException {
        appointmentService.process(request);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Appointment submitted successfully"));
    }
}
