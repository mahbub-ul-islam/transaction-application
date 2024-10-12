package com.example.transactionservice.service.impl;

import com.example.transactionservice.dto.request.TransactionRequestDto;
import com.example.transactionservice.dto.response.PagedResponse;
import com.example.transactionservice.dto.response.TransactionResponseDto;
import com.example.transactionservice.entity.Transaction;
import com.example.transactionservice.exception.errors.resource.EntityNotFoundException;
import com.example.transactionservice.mapper.TransactionMapper;
import com.example.transactionservice.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

//    private Transaction existingTransaction;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;
    private Transaction existingTransaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Set up existing transaction
        existingTransaction = new Transaction();
        existingTransaction.setId(1L);
        existingTransaction.setAccountNumber("654321");
        existingTransaction.setDescription("description");
        existingTransaction.setTrxTime("time");
        existingTransaction.setTrxDate("date");
        existingTransaction.setTrxAmount(10.0);
        existingTransaction.setCustomerId("customerId");

        // Set up transaction request DTO
        transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setAccountNumber("654321");
        transactionRequestDto.setDescription("description");
        transactionRequestDto.setTrxTime("time");
        transactionRequestDto.setTrxDate("date");
        transactionRequestDto.setTrxAmount(10.0);
        transactionRequestDto.setCustomerId("customerId");

        // Set up transaction response DTO
        transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setId(1L);
        transactionResponseDto.setAccountNumber("654321");
        transactionResponseDto.setDescription("description");
        transactionResponseDto.setTrxTime("time");
        transactionResponseDto.setTrxDate("date");
        transactionResponseDto.setTrxAmount(10.0);
        transactionResponseDto.setCustomerId("customerId");
    }

    @Test
    public void testSaveTransaction_Success() {
        // Mock the conversion from DTO to Entity
        when(transactionMapper.toEntity(any(TransactionRequestDto.class))).thenReturn(existingTransaction);

        // Mock the repository save call to return the transaction entity
        when(transactionRepository.save(any(Transaction.class))).thenReturn(existingTransaction);

        // Mock the conversion from Entity to Response DTO
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionResponseDto);

        // Execute the service method to test
        TransactionResponseDto savedTransaction = transactionService.save(transactionRequestDto);

        // Assertions to check if the service returned the expected response DTO
        assertNotNull(savedTransaction);
        assertEquals(transactionResponseDto, savedTransaction);

        // Verify that the save method was called exactly once
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testGetTransactionById_Success() {
        // Arrange
        when(transactionRepository.findById(eq(1L))).thenReturn(Optional.of(existingTransaction));
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionResponseDto);

        // Act
        TransactionResponseDto response = transactionService.getTransactionById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(transactionResponseDto.getId(), response.getId()); // Validate response ID
        assertEquals(transactionResponseDto.getAccountNumber(), response.getAccountNumber()); // Validate account number
        verify(transactionRepository, times(1)).findById(1L); // Verify repository interaction
    }

    @Test
    public void testGetTransactionById_NotFound() {
        // Mock the repository to return empty when looking for the transaction by ID
        when(transactionRepository.findById(eq(1L))).thenReturn(Optional.empty());

        // Expect the custom EntityNotFoundException
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            transactionService.getTransactionById(1L);
        });

        // Assert that the exception message is as expected
        assertEquals("Transaction not found with ID: 1", exception.getMessage());

        // Verify that the repository's findById method was called once
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateTransaction_Success() {
        // Arrange
        when(transactionRepository.findById(eq(1L))).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            transactionService.updateTransaction(1L, transactionRequestDto);
        });

        assertEquals("Transaction not found with ID: 1", exception.getMessage());
        verify(transactionRepository, times(1)).findById(1L); // Verify findById was called once
        verify(transactionRepository, never()).save(any(Transaction.class)); // Ensure save was never called
    }

    @Test
    public void testUpdateTransaction_NotFound() {
        when(transactionRepository.findById(eq(1L))).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            transactionService.updateTransaction(1L, transactionRequestDto);
        });

        assertEquals("Transaction not found with ID: 1", exception.getMessage());
        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    public void testGetAllTransactions_Success() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(existingTransaction);
        when(transactionRepository.findAll()).thenReturn(transactions);
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionResponseDto);

        List<TransactionResponseDto> allTransactions = transactionService.getAllTransactions();

        assertNotNull(allTransactions);
        assertEquals(1, allTransactions.size());
        verify(transactionRepository, times(1)).findAll();
    }


    @Test
    public void testGetAllTransactionsWithPagination_Success() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(existingTransaction);

        Page<Transaction> pagedTransactions = new PageImpl<>(transactions);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));

        when(transactionRepository.findAll(pageable)).thenReturn(pagedTransactions);
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionResponseDto);

        PagedResponse<TransactionResponseDto> pagedResponse = transactionService.getAllTransactionsWithPagination(0, 10);

        assertNotNull(pagedResponse);
        assertEquals(1, pagedResponse.getData().size());
        verify(transactionRepository, times(1)).findAll(pageable);
    }
}