package com.example.authservice.exception.errors.auth;

import com.example.authservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class UnauthorizedAccessException extends ApplicationException {
    public UnauthorizedAccessException(String message) {
        super(message, "UNAUTHORIZED_ACCESS", HttpStatus.FORBIDDEN);
    }
}
