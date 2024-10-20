package com.example.transactionservice.exception.errors.validation;

import com.example.transactionservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidInputException extends ApplicationException {
    public InvalidInputException(String message) {
        super(message, "INVALID_INPUT", HttpStatus.BAD_REQUEST);
    }
}
