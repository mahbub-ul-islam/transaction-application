package com.example.transactionservice.exception.errors.application;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    private String errorCode;
    private HttpStatus httpStatus;

    // Constructor with error message and error code, defaulting to INTERNAL_SERVER_ERROR
    public ApplicationException(String message, String errorCode) {
        this(message, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Constructor with error message, error code, and HTTP status
    public ApplicationException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
