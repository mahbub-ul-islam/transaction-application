package com.example.transactionservice.service;

import com.example.transactionservice.dto.request.TransactionRequestDto;
import com.example.transactionservice.dto.response.PagedResponse;
import com.example.transactionservice.dto.response.TransactionResponseDto;
import com.example.transactionservice.entity.Transaction;

import java.util.List;

public interface TransactionService {

    TransactionResponseDto save(TransactionRequestDto transactionRequestDto);

    TransactionResponseDto getTransactionById(Long id);

    TransactionResponseDto updateTransaction(Long id, TransactionRequestDto transactionRequestDto);

    List<TransactionResponseDto> getAllTransactions();

    PagedResponse<TransactionResponseDto> getAllTransactionsWithPagination(int page, int size);


}
