package com.example.transactionservice.exception.errors.validation;

import com.example.transactionservice.exception.errors.application.ApplicationException;

public class ConstraintViolationException extends ApplicationException {
    public ConstraintViolationException(String message) {
        super(message, "CONSTRAINT_VIOLATION_ERROR");
    }
}
