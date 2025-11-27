package com.srd.clinic.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"privacyAgreed"})
public class AppointmentRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Mobile number is required required")
    @Pattern(regexp = "^09\\d{9}$", message = "Invalid mobile number")
    private String mobile;

    @NotBlank(message = "Service is required")
    private String service;

    @NotBlank(message = "Preferred date is required")
    private String preferredDate;

    @NotBlank(message = "Preferred time is required")
    private String preferredTime;

    @Max(value = 150, message = "Notes cannot exceed 150 characters")
    private String notes;

    @NotBlank(message = "Captcha token is required")
    private String captchaToken;

}
