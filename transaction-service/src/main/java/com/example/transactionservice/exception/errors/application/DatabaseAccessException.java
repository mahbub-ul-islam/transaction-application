package com.example.transactionservice.exception.errors.application;

public class DatabaseAccessException extends ApplicationException {
    public DatabaseAccessException(String message) {
        super(message, "DB_ACCESS_ERROR");
    }
}
