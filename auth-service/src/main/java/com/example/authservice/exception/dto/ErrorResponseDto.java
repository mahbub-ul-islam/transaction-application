package com.example.authservice.exception.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDto {

    private boolean success;
    private String message;
    private String errorCode;
    private int status;
    private LocalDateTime timestamp;
    private String details;
}