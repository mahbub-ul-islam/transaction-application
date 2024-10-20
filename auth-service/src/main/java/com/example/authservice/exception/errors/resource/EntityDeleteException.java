package com.example.authservice.exception.errors.resource;


import com.example.authservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class EntityDeleteException extends ApplicationException {
    public EntityDeleteException(String message) {
        super(message, "ENTITY_DELETE_ERROR", HttpStatus.BAD_REQUEST);
    }
}

