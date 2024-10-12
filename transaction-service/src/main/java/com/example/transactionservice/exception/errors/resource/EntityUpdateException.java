package com.example.transactionservice.exception.errors.resource;

import com.example.transactionservice.exception.errors.application.ApplicationException;

public class EntityUpdateException extends ApplicationException {
    public EntityUpdateException(String message) {
        super(message, "ENTITY_UPDATE_ERROR");
    }
}
