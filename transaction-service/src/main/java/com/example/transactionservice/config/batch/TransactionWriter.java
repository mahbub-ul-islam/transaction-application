package com.example.transactionservice.config.batch;

import com.example.transactionservice.entity.Transaction;
import com.example.transactionservice.exception.errors.batch.BatchJobFailureException;
import com.example.transactionservice.repository.TransactionRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class TransactionWriter implements ItemWriter<Transaction> {

    private final TransactionRepository repository;

    public TransactionWriter(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void write(Chunk<? extends Transaction> items) throws Exception {
        for (Transaction item : items) {
            try {
//                System.out.println("Writing transaction: " + item);
                repository.save(item);
            } catch (Exception e) {
                throw new BatchJobFailureException("Error saving transaction: " + item);
            }
        }
    }
}