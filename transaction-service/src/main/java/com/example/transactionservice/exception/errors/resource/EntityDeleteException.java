package com.example.transactionservice.exception.errors.resource;

import com.example.transactionservice.exception.errors.application.ApplicationException;

public class EntityDeleteException extends ApplicationException {
    public EntityDeleteException(String message) {
        super(message, "ENTITY_DELETE_ERROR");
    }
}

