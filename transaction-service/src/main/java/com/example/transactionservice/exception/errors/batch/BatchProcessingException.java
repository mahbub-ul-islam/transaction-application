package com.example.transactionservice.exception.errors.batch;

import com.example.transactionservice.exception.errors.application.ApplicationException;

public class BatchProcessingException extends ApplicationException {

    public BatchProcessingException(String message) {
        this(message, "BATCH_PROCESSING_ERROR");
    }

    public BatchProcessingException(String message, String errorCode) {
        super(message, errorCode);
    }
}
