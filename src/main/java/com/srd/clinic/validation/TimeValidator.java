package com.srd.clinic.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class TimeValidator implements ConstraintValidator<ValidTime, String> {

    // Pattern for 24-hour format: HH:MM (00:00 to 23:59)
    private static final Pattern TIME_PATTERN = Pattern.compile(
            "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Let @NotBlank handle null/empty validation
        }

        String trimmedValue = value.trim();

        if (!TIME_PATTERN.matcher(trimmedValue).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Invalid time format. Use format: HH:MM AM/PM (e.g., 12:00 PM or 01:30 AM)")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}