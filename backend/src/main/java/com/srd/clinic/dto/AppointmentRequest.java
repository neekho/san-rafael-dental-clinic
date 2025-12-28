package com.srd.clinic.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.srd.clinic.error.ErrorCode;
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

    @NotBlank(message = ErrorCode.FIRST_NAME_REQUIRED)
    @Pattern(regexp = "^[a-zA-Z\\s'-]*$", message = ErrorCode.FIRST_NAME_INVALID_CHARACTERS)
    private String firstName;

    @NotBlank(message = ErrorCode.LAST_NAME_REQUIRED)
    @Pattern(regexp = "^[a-zA-Z\\s'-]*$", message = ErrorCode.LAST_NAME_INVALID_CHARACTERS)
    private String lastName;

    @Email(message = ErrorCode.EMAIL_INVALID)
    private String email;

    @NotBlank(message = ErrorCode.MOBILE_REQUIRED)
    private String mobile;

    @NotBlank(message = ErrorCode.SERVICE_REQUIRED)
    @ValidService
    private String service;

    @NotBlank(message = ErrorCode.DATE_REQUIRED)
    @ValidateDate
    private String preferredDate;

    @NotBlank(message = ErrorCode.TIME_REQUIRED)
    @ValidTime
    private String preferredTime;

    @Size(max= 150, message = ErrorCode.NOTES_MAX_LENGTH_EXCEEDED)
    private String notes;

    @NotBlank(message = ErrorCode.CAPTCHA_TOKEN_REQUIRED)
    private String captchaToken;

}
