package com.example.authservice.exception.errors.auth;

import com.example.authservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class AccountDisabledException extends ApplicationException {
    public AccountDisabledException(String message) {
        super(message, "ACCOUNT_DISABLED", HttpStatus.FORBIDDEN);
    }
}
