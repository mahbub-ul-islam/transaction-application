package com.example.transactionservice.service.impl;

import com.example.transactionservice.dto.request.FilterRequestDto;
import com.example.transactionservice.dto.request.TransactionRequestDto;
import com.example.transactionservice.dto.response.TransactionResponseDto;
import com.example.transactionservice.entity.Transaction;
import com.example.transactionservice.exception.errors.resource.EntityNotFoundException;
import com.example.transactionservice.mapper.TransactionMapper;
import com.example.transactionservice.repository.TransactionRepository;
import com.example.transactionservice.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper = TransactionMapper.INSTANCE;

    @Override
    public TransactionResponseDto save(TransactionRequestDto transactionRequestDto) {
        Transaction transactionRequest = transactionMapper.toEntity(transactionRequestDto);
        Transaction transactionResponse = transactionRepository.save(transactionRequest);
        return transactionMapper.toDto(transactionResponse);
    }

    @Override
    public TransactionResponseDto getTransactionById(Long id) {
        Transaction transaction = this.getTransactionEntity(id);
        return transactionMapper.toDto(transaction);
    }

    private Transaction getTransactionEntity(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + id));
    }

    @Transactional
    @Override
    public TransactionResponseDto updateTransaction(Long id, TransactionRequestDto transactionRequestDto) {
        Transaction existingTransaction = this.getTransactionEntity(id);
        existingTransaction.setAccountNumber(transactionRequestDto.getAccountNumber());
        existingTransaction.setTrxAmount(transactionRequestDto.getTrxAmount());
        existingTransaction.setDescription(transactionRequestDto.getDescription());
        existingTransaction.setTrxDate(transactionRequestDto.getTrxDate());
        existingTransaction.setTrxTime(transactionRequestDto.getTrxTime());
        existingTransaction.setCustomerId(transactionRequestDto.getCustomerId());

        Transaction updatedTransaction = transactionRepository.save(existingTransaction);
        return transactionMapper.toDto(updatedTransaction);
    }

    @Override
    public Page<TransactionResponseDto> searchTransactions(FilterRequestDto dto) {
        int page = dto.getPage();
        int size = dto.getSize();
        String customerId = dto.getCustomerId() != null ? dto.getCustomerId() : "";
        String accountNumber = dto.getAccountNumber() != null ? dto.getAccountNumber() : "";
        String description = dto.getDescription() != null ? dto.getDescription() : "";

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));

        if (customerId.isEmpty() && accountNumber.isEmpty() && description.isEmpty()) {
            return transactionRepository.findAll(pageable)
                    .map(transactionMapper::toDto);
        }

        return transactionRepository.findByCustomerIdContainingAndAccountNumberContainingAndDescriptionContaining(
                customerId, accountNumber, description, pageable
        ).map(transactionMapper::toDto);
    }
}
