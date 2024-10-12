package com.example.transactionservice.exception.handler;

import com.example.transactionservice.exception.dto.ErrorResponseDto;
import com.example.transactionservice.exception.errors.application.ApplicationException;
import com.example.transactionservice.exception.errors.validation.DataValidationException;
import com.example.transactionservice.exception.errors.validation.InvalidInputException;
import com.example.transactionservice.exception.errors.validation.MissingFieldException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle ApplicationException and log it
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponseDto> handleApplicationException(ApplicationException ex) {
        // Log the exception details
        log.error("{} occurred: {} - {}", ex.getClass().getSimpleName(), ex.getErrorCode(), ex.getMessage(), ex);

        // Build and return the response using the exception's HTTP status
        ErrorResponseDto response = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .status(ex.getHttpStatus().value()) // Use the status from the exception
                .details("Error code: " + ex.getErrorCode() + ". Please contact support if this issue persists.") // User-friendly detail
                .build();

        return new ResponseEntity<>(response, ex.getHttpStatus()); // Use the exception's HTTP status
    }




    // Handle MethodArgumentNotValidException for @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errors = new StringBuilder();

        // Collect all field errors and append them to the error message
        for (FieldError error : bindingResult.getFieldErrors()) {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.append(String.format("%s: %s; ", fieldName, errorMessage));
        }

        // Log validation errors
        log.warn("Validation errors occurred: {}", errors.toString());

        // Build the error response
        ErrorResponseDto response = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message("Validation failed: " + errors.toString())
                .errorCode("VALIDATION_ERROR")
                .status(HttpStatus.BAD_REQUEST.value())
                .details("Please check the provided data.")
                .build();

        // Return the error response
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }





    // Handle unknown exceptions and log them
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        // Log the exception details
        log.error("Unknown exception occurred: {}", ex.getMessage(), ex);

        // Build and return the response
        ErrorResponseDto response = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message("An unexpected error occurred")
                .errorCode("INTERNAL_SERVER_ERROR")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .details("An unexpected error occurred. Please try again later.") // User-friendly detail
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
