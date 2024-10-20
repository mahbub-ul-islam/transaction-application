package com.example.authservice.exception.handler;

import com.example.authservice.exception.dto.ErrorResponseDto;
import com.example.authservice.exception.errors.application.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponseDto> handleApplicationException(ApplicationException ex) {
        log.error("{} occurred: {} - {}", ex.getClass().getSimpleName(), ex.getErrorCode(), ex.getMessage(), ex);

        ErrorResponseDto response = ErrorResponseDto.builder()
                .success(false)
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .status(ex.getHttpStatus().value())
                .details("Error code: " + ex.getErrorCode() + ". Please contact support if this issue persists.")
                .build();
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        log.error("Unknown exception occurred: {}", ex.getMessage(), ex);

        ErrorResponseDto response = ErrorResponseDto.builder()
                .success(false)
                .timestamp(LocalDateTime.now())
                .message("An unexpected error occurred")
                .errorCode("INTERNAL_SERVER_ERROR")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .details("An unexpected error occurred. Please try again later.")
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
