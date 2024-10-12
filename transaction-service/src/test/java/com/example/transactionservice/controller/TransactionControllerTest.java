package com.example.transactionservice.controller;

import com.example.transactionservice.dto.request.TransactionRequestDto;
import com.example.transactionservice.dto.response.PagedResponse;
import com.example.transactionservice.dto.response.TransactionResponseDto;
import com.example.transactionservice.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private TransactionController transactionController;

    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionRequestDto = new TransactionRequestDto(); // Initialize with proper data
        transactionResponseDto = new TransactionResponseDto(); // Initialize with proper data
    }


    @Test
    void testSaveTransaction_Success() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(transactionService.save(any(TransactionRequestDto.class))).thenReturn(transactionResponseDto);

        ResponseEntity<TransactionResponseDto> response = transactionController.saveTransaction(transactionRequestDto, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transactionResponseDto, response.getBody());
        verify(transactionService, times(1)).save(any(TransactionRequestDto.class));
    }

    @Test
    public void testSaveTransaction_ValidationError() {
        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<TransactionResponseDto> response = transactionController.saveTransaction(transactionRequestDto, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(transactionService, times(0)).save(any(TransactionRequestDto.class));
    }

    @Test
    public void testGetTransactionById_Success() {
        when(transactionService.getTransactionById(1L)).thenReturn(transactionResponseDto);

        ResponseEntity<TransactionResponseDto> response = transactionController.getTransactionById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionResponseDto, response.getBody());
        verify(transactionService, times(1)).getTransactionById(1L);
    }

    @Test
    public void testUpdateTransaction_Success() {
        when(transactionService.updateTransaction(eq(1L), any(TransactionRequestDto.class))).thenReturn(transactionResponseDto);

        ResponseEntity<TransactionResponseDto> response = transactionController.updateTransaction(1L, transactionRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionResponseDto, response.getBody());
        verify(transactionService, times(1)).updateTransaction(eq(1L), any(TransactionRequestDto.class));
    }

    @Test
    public void testGetAllTransactions_Success() {
        List<TransactionResponseDto> transactions = new ArrayList<>();
        transactions.add(transactionResponseDto);

        when(transactionService.getAllTransactions()).thenReturn(transactions);

        ResponseEntity<List<TransactionResponseDto>> response = transactionController.getAllTransactions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactions, response.getBody());
        verify(transactionService, times(1)).getAllTransactions();
    }

    @Test
    public void testGetAllTransactionsWithPagination_Success() {
        PagedResponse<TransactionResponseDto> pagedResponse = new PagedResponse<>();
        when(transactionService.getAllTransactionsWithPagination(0, 10)).thenReturn(pagedResponse);

        ResponseEntity<PagedResponse<TransactionResponseDto>> response = transactionController.getAllTransactions(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagedResponse, response.getBody());
        verify(transactionService, times(1)).getAllTransactionsWithPagination(0, 10);
    }
}