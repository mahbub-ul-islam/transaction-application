package com.example.transactionservice.exception.errors.batch;

public class InvalidBatchDataException extends BatchProcessingException {
    public InvalidBatchDataException(String message) {
        super(message, "INVALID_BATCH_DATA");
    }
}
