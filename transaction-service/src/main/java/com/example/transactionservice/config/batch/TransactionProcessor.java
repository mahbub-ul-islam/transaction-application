package com.example.transactionservice.config.batch;

import com.example.transactionservice.entity.Transaction;
import com.example.transactionservice.exception.errors.batch.BatchProcessingException;
import com.example.transactionservice.exception.errors.batch.InvalidBatchDataException;
import org.springframework.batch.item.ItemProcessor;

public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {

    @Override
    public Transaction process(Transaction item) {
        try {
            if (item.getTrxAmount() < 0) {
                throw new InvalidBatchDataException("Transaction amount cannot be negative: " + item);
            }
            return item;
        } catch (Exception e) {
            throw new BatchProcessingException("Error processing transaction: " + item);
        }
    }
}
