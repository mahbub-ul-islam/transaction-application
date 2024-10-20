package com.example.authservice.exception.errors.application;

public class DatabaseAccessException extends ApplicationException {
    public DatabaseAccessException(String message) {
        super(message, "DB_ACCESS_ERROR");
    }
}
