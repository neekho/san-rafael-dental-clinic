package com.srd.clinic.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class ServiceValidator implements ConstraintValidator<ValidService, String> {

    private static final Set<String> VALID_SERVICES = Set.of(
        "Dental Consultation",
        "Oral Prophylaxis", 
        "Fillings",
        "Tooth Extraction",
        "Odontectomy",
        "Root Canal",
        "Dental Braces",
        "Teeth Whitening",
        "Dentures",
        "Implant Dentistry",
        "Gum Treatment",
        "Oral Surgery",
        "TMD Problems"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Let @NotBlank handle null/empty validation
        }

        String trimmedValue = value.trim();
        
        // Case-insensitive validation
        boolean isValidService = VALID_SERVICES.stream()
            .anyMatch(service -> service.equalsIgnoreCase(trimmedValue));
        
        if (!isValidService) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid service selected. Please choose from the available options")
                   .addConstraintViolation();
            return false;
        }

        return true;
    }
}