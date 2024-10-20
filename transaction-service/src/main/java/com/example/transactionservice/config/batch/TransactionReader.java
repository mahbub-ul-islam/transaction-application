package com.example.transactionservice.config.batch;

import com.example.transactionservice.entity.Transaction;
import com.example.transactionservice.exception.errors.batch.BatchProcessingException;
import com.example.transactionservice.exception.errors.batch.FileFormatException;
import com.example.transactionservice.exception.errors.batch.InvalidBatchDataException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class TransactionReader extends FlatFileItemReader<Transaction> {

    public TransactionReader() {
        setResource(new ClassPathResource("batch/dataSource.txt"));
        setName("txtReader");
        setLinesToSkip(1);
        setLineMapper(lineMapper());
    }

    @Override
    public Transaction read() {
        try {
            Transaction transaction = super.read();

            if (transaction == null) {
                return null;
            }
            validateTransaction(transaction);
            return transaction;
        } catch (IOException e) {
            throw new FileFormatException("Error reading from the file");
        } catch (Exception e) {
            throw new BatchProcessingException("Error processing transaction: " + e.getMessage());
        }
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction.getTrxAmount() < 0) {
            throw new InvalidBatchDataException("Transaction amount cannot be negative: " + transaction);
        }
    }

    private LineMapper<Transaction> lineMapper() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter("|");
        tokenizer.setStrict(false);
        tokenizer.setNames("ACCOUNT_NUMBER", "TRX_AMOUNT", "DESCRIPTION", "TRX_DATE", "TRX_TIME", "CUSTOMER_ID");

        BeanWrapperFieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Transaction.class);

        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}
