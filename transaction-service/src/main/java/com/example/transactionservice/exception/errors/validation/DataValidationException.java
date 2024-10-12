package com.example.transactionservice.exception.errors.validation;

import com.example.transactionservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class DataValidationException extends ApplicationException {
    public DataValidationException(String message) {
        super(message, "DATA_VALIDATION_ERROR", HttpStatus.BAD_REQUEST); // Custom HTTP status
    }
}
