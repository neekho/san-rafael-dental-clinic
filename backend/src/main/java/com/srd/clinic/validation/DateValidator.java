package com.srd.clinic.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<ValidateDate, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Let @NotBlank handle null/empty validation
        }

        try {
            // Parse YYYY-MM-DD format (ISO date format from frontend)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate selectedDate = LocalDate.parse(value, formatter);
            LocalDate today = LocalDate.now();
            LocalDate lastDayOfYear = LocalDate.of(today.getYear(), 12, 31);

            // Check if date is not in the past
            if (selectedDate.isBefore(today)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Cannot book appointments for past dates")
                       .addConstraintViolation();
                return false;
            }

            // Special case: If current month is December, allow booking for January of next year
            if (today.getMonth().equals(Month.DECEMBER) &&
                selectedDate.getYear() == today.getYear() + 1 &&
                selectedDate.getMonth().equals(Month.JANUARY)) {
                return true;
            }

            // Check if date is within current year
            if (selectedDate.isAfter(lastDayOfYear)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Appointments can only be booked within " + today.getYear())
                       .addConstraintViolation();
                return false;
            }

            return true;
        } catch (DateTimeParseException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid date format. Use YYYY-MM-DD")
                   .addConstraintViolation();
            return false;
        }
    }
}