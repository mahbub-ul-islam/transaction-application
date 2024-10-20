package com.example.transactionservice.exception.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDto {

    private LocalDateTime timestamp;
    private String message;
    private String errorCode;
    private int status;
    private String details;
}
