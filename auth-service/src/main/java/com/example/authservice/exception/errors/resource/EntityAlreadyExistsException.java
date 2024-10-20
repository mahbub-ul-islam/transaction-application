package com.example.authservice.exception.errors.resource;

import com.example.authservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends ApplicationException {
    public EntityAlreadyExistsException(String message) {
        super(message, "ENTITY_ALREADY_EXISTS", HttpStatus.CONFLICT);
    }
}
