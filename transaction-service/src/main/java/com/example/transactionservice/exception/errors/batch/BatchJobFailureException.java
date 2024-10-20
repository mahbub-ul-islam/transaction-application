package com.example.transactionservice.exception.errors.batch;

public class BatchJobFailureException extends BatchProcessingException {
    public BatchJobFailureException(String message) {
        super(message, "BATCH_JOB_FAILURE");
    }
}
