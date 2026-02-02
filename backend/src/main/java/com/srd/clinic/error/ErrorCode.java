package com.srd.clinic.error;

import java.time.Year;

public class ErrorCode {
    // First Name validation messages
    public static final String FIRST_NAME_REQUIRED = "First name is required";
    public static final String FIRST_NAME_INVALID_CHARACTERS = "First name can only contain letters, spaces, hyphens, and apostrophes";
    
    // Last Name validation messages
    public static final String LAST_NAME_REQUIRED = "Last name is required";
    public static final String LAST_NAME_INVALID_CHARACTERS = "Last name can only contain letters, spaces, hyphens, and apostrophes";
    
    // Email validation messages
    public static final String EMAIL_INVALID = "Invalid email";
    
    // Mobile validation messages
    public static final String MOBILE_REQUIRED = "Mobile number is required";
    public static final String MOBILE_INVALID_FORMAT = "Invalid mobile number. Use format: 09XXXXXXXXX";
    
    // Service validation messages
    public static final String SERVICE_REQUIRED = "Service is required";
    public static final String SERVICE_INVALID_SELECTION = "Invalid service selected. Please choose from the available options";
    
    // Date validation messages
    public static final String DATE_REQUIRED = "Preferred date is required";
    public static final String DATE_INVALID_FORMAT = "Invalid date format. Use YYYY-MM-DD";
    public static final String DATE_PAST_NOT_ALLOWED = "Cannot book appointments for past dates";
    public static final String DATE_YEAR_LIMIT_EXCEEDED = "Appointments can only be booked within " + Year.now().getValue();
    
    // Time validation messages
    public static final String TIME_REQUIRED = "Preferred time is required";
    public static final String TIME_INVALID_FORMAT = "Invalid time format. Use 24-hour format HH:MM (e.g., 14:30, 09:00, 23:59)";
    
    // Notes validation messages
    public static final String NOTES_MAX_LENGTH_EXCEEDED = "Notes cannot exceed 150 characters";
    
    // Captcha validation messages
    public static final String CAPTCHA_TOKEN_REQUIRED = "Captcha token is required";
}
