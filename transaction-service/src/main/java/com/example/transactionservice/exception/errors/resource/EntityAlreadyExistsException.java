package com.example.transactionservice.exception.errors.resource;

import com.example.transactionservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends ApplicationException {
    public EntityAlreadyExistsException(String message) {
        super(message, "ENTITY_ALREADY_EXISTS", HttpStatus.CONFLICT);
    }
}
