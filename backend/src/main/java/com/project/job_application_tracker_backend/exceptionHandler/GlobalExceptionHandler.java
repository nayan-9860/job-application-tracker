package com.project.job_application_tracker_backend.exceptionHandler;

import com.project.job_application_tracker_backend.dto.ApiErrorDto;
import com.project.job_application_tracker_backend.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleResourceNotFound(
            ResourceNotFoundException ex) {

        ApiErrorDto error = new ApiErrorDto(
                false,
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Validation errors from DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDto> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err ->
                validationErrors.put(err.getField(), err.getDefaultMessage())
        );

        ApiErrorDto error = new ApiErrorDto(
                false,
                "Validation failed",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                validationErrors
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Login failure
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorDto> handleBadCredentials(
            BadCredentialsException ex) {

        ApiErrorDto error = new ApiErrorDto(
                false,
                "Invalid email or password",
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    // Access denied
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorDto> handleAccessDenied(
            AccessDeniedException ex) {

        ApiErrorDto error = new ApiErrorDto(
                false,
                "Access denied",
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    // Fallback exception (last handler)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDto> handleGeneralException(
            Exception ex) {

        ApiErrorDto error = new ApiErrorDto(
                false,
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
