package com.example.authservice.exception.errors.resource;

import com.example.authservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ApplicationException {
    public EntityNotFoundException(String message) {
        super(message, "ENTITY_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
