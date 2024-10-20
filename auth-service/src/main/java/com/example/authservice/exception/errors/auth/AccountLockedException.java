package com.example.authservice.exception.errors.auth;

import com.example.authservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class AccountLockedException extends ApplicationException {
    public AccountLockedException(String message) {
        super(message, "ACCOUNT_LOCKED", HttpStatus.LOCKED);
    }
}
