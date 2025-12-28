package com.srd.clinic.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.srd.clinic.validation.ValidateDate;
import com.srd.clinic.validation.ValidTime;
import com.srd.clinic.validation.ValidService;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true, value = {"privacyAgreed"})
public class AppointmentRequest {

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z\\s'-]*$", message = "First name can only contain letters, spaces, hyphens, and apostrophes")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z\\s'-]*$", message = "Last name can only contain letters, spaces, hyphens, and apostrophes")
    private String lastName;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^(09\\d{9})?$", message = "Invalid mobile number. Use format: 09XXXXXXXXX")
    private String mobile;

    @NotBlank(message = "Service is required")
    @ValidService
    private String service;

    @NotBlank(message = "Preferred date is required")
    @ValidateDate
    private String preferredDate;

    @NotBlank(message = "Preferred time is required")
    @ValidTime
    private String preferredTime;

    @Size(max= 150, message = "Notes cannot exceed 150 characters")
    private String notes;

    @NotBlank(message = "Captcha token is required")
    private String captchaToken;

}
