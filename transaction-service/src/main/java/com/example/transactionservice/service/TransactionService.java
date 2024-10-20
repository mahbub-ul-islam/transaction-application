package com.example.transactionservice.service;

import com.example.transactionservice.dto.request.FilterRequestDto;
import com.example.transactionservice.dto.request.TransactionRequestDto;
import com.example.transactionservice.dto.response.TransactionResponseDto;
import org.springframework.data.domain.Page;

public interface TransactionService {

    TransactionResponseDto save(TransactionRequestDto transactionRequestDto);

    TransactionResponseDto getTransactionById(Long id);

    TransactionResponseDto updateTransaction(Long id, TransactionRequestDto transactionRequestDto);

    Page<TransactionResponseDto> searchTransactions(FilterRequestDto dto);
}
