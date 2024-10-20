package com.example.transactionservice.exception.errors.validation;

import com.example.transactionservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class MissingFieldException extends ApplicationException {
    public MissingFieldException(String message) {
        super(message, "MISSING_FIELD_ERROR", HttpStatus.BAD_REQUEST);
    }
}
