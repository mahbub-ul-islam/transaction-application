package com.example.transactionservice.exception.errors.resource;

import com.example.transactionservice.exception.errors.application.ApplicationException;
import org.springframework.http.HttpStatus;

public class TransactionOptimisticLockException extends ApplicationException {
    public TransactionOptimisticLockException(String message) {
        super(message, "TRANSACTION_OPTIMISTIC_LOCK_FAILURE", HttpStatus.CONFLICT);
    }
}
