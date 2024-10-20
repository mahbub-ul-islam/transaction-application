package com.example.transactionservice.service.impl;

import com.example.transactionservice.dto.request.FilterRequestDto;
import com.example.transactionservice.dto.request.TransactionRequestDto;
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

import java.util.Collections;
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

    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;
    private Transaction existingTransaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        existingTransaction = new Transaction();
        existingTransaction.setId(1L);
        existingTransaction.setAccountNumber("654321");
        existingTransaction.setDescription("description");
        existingTransaction.setTrxTime("time");
        existingTransaction.setTrxDate("date");
        existingTransaction.setTrxAmount(10.0);
        existingTransaction.setCustomerId("customerId");

        transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setAccountNumber("654321");
        transactionRequestDto.setDescription("description");
        transactionRequestDto.setTrxTime("time");
        transactionRequestDto.setTrxDate("date");
        transactionRequestDto.setTrxAmount(10.0);
        transactionRequestDto.setCustomerId("customerId");

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
    public void test_SaveTransaction_Success() {
        when(transactionMapper.toEntity(any(TransactionRequestDto.class))).thenReturn(existingTransaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(existingTransaction);
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionResponseDto);

        TransactionResponseDto savedTransaction = transactionService.save(transactionRequestDto);

        assertNotNull(savedTransaction);
        assertEquals(transactionResponseDto, savedTransaction);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void test_GetTransactionById_Success() {
        when(transactionRepository.findById(eq(1L))).thenReturn(Optional.of(existingTransaction));
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionResponseDto);

        TransactionResponseDto response = transactionService.getTransactionById(1L);

        assertNotNull(response);
        assertEquals(transactionResponseDto.getId(), response.getId());
        assertEquals(transactionResponseDto.getAccountNumber(), response.getAccountNumber());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    public void test_GetTransactionById_NotFound() {
        when(transactionRepository.findById(eq(1L))).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            transactionService.getTransactionById(1L);
        });

        assertEquals("Transaction not found with ID: 1", exception.getMessage());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    public void test_UpdateTransaction_Success() {
        when(transactionRepository.findById(eq(1L))).thenReturn(Optional.of(existingTransaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(existingTransaction);
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionResponseDto);

        TransactionResponseDto updatedTransaction = transactionService.updateTransaction(1L, transactionRequestDto);

        assertNotNull(updatedTransaction);
        assertEquals(transactionResponseDto, updatedTransaction);
        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).save(existingTransaction);
    }

    @Test
    public void test_UpdateTransaction_NotFound() {
        when(transactionRepository.findById(eq(1L))).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            transactionService.updateTransaction(1L, transactionRequestDto);
        });

        assertEquals("Transaction not found with ID: 1", exception.getMessage());
        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    public void test_SearchTransactions_WithFiltersSuccess() {
        FilterRequestDto filterRequestDto = new FilterRequestDto();
        filterRequestDto.setPage(0);
        filterRequestDto.setSize(10);
        filterRequestDto.setCustomerId("customerId");
        Page<Transaction> transactionPage = new PageImpl<>(Collections.singletonList(existingTransaction));

        when(transactionRepository.findByCustomerIdContainingAndAccountNumberContainingAndDescriptionContaining(
                anyString(), anyString(), anyString(), any(Pageable.class)))
                .thenReturn(transactionPage);
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionResponseDto);

        Page<TransactionResponseDto> result = transactionService.searchTransactions(filterRequestDto);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(transactionResponseDto, result.getContent().get(0));
        verify(transactionRepository, times(1)).findByCustomerIdContainingAndAccountNumberContainingAndDescriptionContaining(
                anyString(), anyString(), anyString(), any(Pageable.class));
    }

    @Test
    public void testSearchTransactions_NoFiltersSuccess() {

        FilterRequestDto filterRequestDto = new FilterRequestDto();
        filterRequestDto.setPage(0);
        filterRequestDto.setSize(10);

        Page<Transaction> transactionPage = new PageImpl<>(Collections.singletonList(existingTransaction));
        when(transactionRepository.findAll(any(Pageable.class))).thenReturn(transactionPage);
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionResponseDto);

        Page<TransactionResponseDto> result = transactionService.searchTransactions(filterRequestDto);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(transactionResponseDto, result.getContent().get(0));
        verify(transactionRepository, times(1)).findAll(any(Pageable.class));
    }
}