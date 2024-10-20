package com.example.authservice.exception.errors.auth;

import com.example.authservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class PasswordExpiredException extends ApplicationException {
    public PasswordExpiredException(String message) {
        super(message, "PASSWORD_EXPIRED", HttpStatus.FORBIDDEN);
    }
}
