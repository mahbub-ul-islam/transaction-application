package com.example.transactionservice.service.impl;

import com.example.transactionservice.dto.request.TransactionRequestDto;
import com.example.transactionservice.dto.response.PagedResponse;
import com.example.transactionservice.dto.response.TransactionResponseDto;
import com.example.transactionservice.entity.Transaction;
import com.example.transactionservice.exception.errors.resource.EntityNotFoundException;
import com.example.transactionservice.mapper.TransactionMapper;
import com.example.transactionservice.repository.TransactionRepository;
import com.example.transactionservice.service.TransactionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper = TransactionMapper.INSTANCE;

    @PersistenceContext
    private EntityManager entityManager;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionResponseDto save(TransactionRequestDto transactionRequestDto) {
        System.out.println(transactionRequestDto);
        Transaction transactionRequest = transactionMapper.toEntity(transactionRequestDto);
        Transaction transactionResponse = transactionRepository.save(transactionRequest);
        System.out.println(transactionResponse);
        return transactionMapper.toDto(transactionResponse);
    }

    private Transaction getTransactionEntity(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + id));
    }

    @Override
    public TransactionResponseDto getTransactionById(Long id) {
        Transaction transaction = this.getTransactionEntity(id);
        System.out.println(transaction);
        return transactionMapper.toDto(transaction);
    }

    @Transactional
    @Override
    public TransactionResponseDto updateTransaction(Long id, TransactionRequestDto transactionRequestDto) {
        System.out.println(transactionRequestDto);
        Transaction existingTransaction = this.getTransactionEntity(id);
        System.out.println(existingTransaction);
        boolean isManaged = entityManager.contains(existingTransaction);
        System.out.println("Is entity managed: " + isManaged);

        existingTransaction.setAccountNumber(transactionRequestDto.getAccountNumber());
        existingTransaction.setTrxAmount(transactionRequestDto.getTrxAmount());
        existingTransaction.setDescription(transactionRequestDto.getDescription());
        existingTransaction.setTrxDate(transactionRequestDto.getTrxDate());
        existingTransaction.setTrxTime(transactionRequestDto.getTrxTime());
        existingTransaction.setCustomerId(transactionRequestDto.getCustomerId());
        System.out.println(existingTransaction);


        Transaction updatedTransaction = transactionRepository.save(existingTransaction);
        System.out.println(updatedTransaction);
        return transactionMapper.toDto(updatedTransaction);
    }


    @Override
    public List<TransactionResponseDto> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transactionMapper::toDto)
                .toList();
    }


    @Override
    public PagedResponse<TransactionResponseDto> getAllTransactionsWithPagination(int page, int size) {
        // Create a Pageable instance with sorting
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));

        // Fetch transactions from the repository
        Page<Transaction> transactions = transactionRepository.findAll(pageable);

        // Convert Page to List
        List<TransactionResponseDto> transactionsResponse =
                transactions.stream()
                        .map(transactionMapper::toDto).toList();

        // Create and return a PagedResponse object
        return new PagedResponse<>(
                transactionsResponse,
                transactions.getNumber(),
                transactions.getSize(),
                transactions.getTotalElements(),
                transactions.getTotalPages());
    }


}
