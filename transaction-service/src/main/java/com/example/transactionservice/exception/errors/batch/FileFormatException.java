package com.example.transactionservice.exception.errors.batch;

public class FileFormatException extends BatchProcessingException {
    public FileFormatException(String message) {
        super(message, "FILE_FORMAT_ERROR");
    }
}
