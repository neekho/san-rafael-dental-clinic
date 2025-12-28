package com.srd.clinic.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(new ApiError("VALIDATION_ERROR", msg));
    }

    @ExceptionHandler(ConfigurationException.class)
    public ResponseEntity<ApiError> handleConfiguration(ConfigurationException ex) {
        return ResponseEntity.internalServerError().body(new ApiError("CONFIGURATION_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntime(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new ApiError("ERROR", ex.getMessage()));
    }
}
