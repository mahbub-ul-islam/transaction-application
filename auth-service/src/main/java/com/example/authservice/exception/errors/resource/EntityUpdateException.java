package com.example.authservice.exception.errors.resource;


import com.example.authservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class EntityUpdateException extends ApplicationException {
    public EntityUpdateException(String message) {
        super(message, "ENTITY_UPDATE_ERROR", HttpStatus.BAD_REQUEST);
    }
}
