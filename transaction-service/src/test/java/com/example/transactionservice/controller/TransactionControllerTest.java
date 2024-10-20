package com.example.transactionservice.controller;

import com.example.transactionservice.dto.request.FilterRequestDto;
import com.example.transactionservice.dto.request.TransactionRequestDto;
import com.example.transactionservice.dto.response.TransactionResponseDto;
import com.example.transactionservice.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Collections;

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
    private FilterRequestDto filterRequestDto;
    private Page<TransactionResponseDto> transactionPage;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionRequestDto = new TransactionRequestDto();
        transactionResponseDto = new TransactionResponseDto();

        filterRequestDto = new FilterRequestDto();
        Pageable pageable = PageRequest.of(0, 10);
        transactionPage = new PageImpl<>(Collections.singletonList(transactionResponseDto), pageable, 1);
    }


    @Test
    void test_SaveTransaction_Success() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(transactionService.save(any(TransactionRequestDto.class))).thenReturn(transactionResponseDto);

        ResponseEntity<TransactionResponseDto> response =
                transactionController.saveTransaction(transactionRequestDto, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transactionResponseDto, response.getBody());
        verify(transactionService, times(1)).save(any(TransactionRequestDto.class));
    }

    @Test
    public void test_SaveTransaction_ValidationError() {
        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<TransactionResponseDto> response = transactionController.saveTransaction(transactionRequestDto, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(transactionService, times(0)).save(any(TransactionRequestDto.class));
    }

    @Test
    public void test_GetTransactionById_Success() {
        when(transactionService.getTransactionById(1L)).thenReturn(transactionResponseDto);

        ResponseEntity<TransactionResponseDto> response = transactionController.getTransactionById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionResponseDto, response.getBody());
        verify(transactionService, times(1)).getTransactionById(1L);
    }

    @Test
    public void test_UpdateTransaction_Success() {
        when(transactionService.updateTransaction(eq(1L), any(TransactionRequestDto.class))).thenReturn(transactionResponseDto);

        ResponseEntity<TransactionResponseDto> response = transactionController.updateTransaction(1L, transactionRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionResponseDto, response.getBody());
        verify(transactionService, times(1)).updateTransaction(eq(1L), any(TransactionRequestDto.class));
    }


    @Test
    void test_SearchTransactions_Success() {
        when(transactionService.searchTransactions(any(FilterRequestDto.class))).thenReturn(transactionPage);

        ResponseEntity<Page<TransactionResponseDto>> response =
                transactionController.searchTransactions1(filterRequestDto);

        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionPage, response.getBody());
        verify(transactionService, times(1)).searchTransactions(any(FilterRequestDto.class));
    }
}