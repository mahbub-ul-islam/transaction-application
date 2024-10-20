package com.example.authservice.exception.errors.auth;

import com.example.authservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApplicationException {
    public InvalidCredentialsException(String message) {
        super(message, "INVALID_CREDENTIALS", HttpStatus.BAD_REQUEST);
    }
}
