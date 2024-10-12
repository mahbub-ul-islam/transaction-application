package com.example.transactionservice.controller;

import com.example.transactionservice.dto.request.TransactionRequestDto;
import com.example.transactionservice.dto.response.PagedResponse;
import com.example.transactionservice.dto.response.TransactionResponseDto;
import com.example.transactionservice.entity.Transaction;
import com.example.transactionservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("api/v1/transaction")
@Tag(name = "Transaction API")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping()
    @Operation(summary = "save transaction")
    public ResponseEntity<TransactionResponseDto> saveTransaction(
            @Valid @RequestBody TransactionRequestDto transactionRequestDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors occurred: {}", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(null);
        }
        log.info("Creating transactions");
        TransactionResponseDto responseDto = transactionService.save(transactionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get transaction")
    public ResponseEntity<TransactionResponseDto> getTransactionById(@PathVariable Long id) {
        log.info("Fetching transaction with ID: {}", id);
        TransactionResponseDto transactionResponseDto = transactionService.getTransactionById(id);
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update transaction")
    public ResponseEntity<TransactionResponseDto> updateTransaction(
            @PathVariable Long id,
            @RequestBody TransactionRequestDto transactionRequestDto) {
        log.info("Updating transaction with ID: {}", id);
        TransactionResponseDto transactionResponseDto = transactionService.updateTransaction(id, transactionRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDto);
    }


    @GetMapping
    @Operation(summary = "get all transaction")
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactions() {
        log.info("Fetching all transactions");
        List<TransactionResponseDto> response = transactionService.getAllTransactions();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/pagination")
    @Operation(summary = "get all transaction with pagination")
    public ResponseEntity<PagedResponse<TransactionResponseDto>> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Fetching all transactions with pagination");
        PagedResponse<TransactionResponseDto> response =
                transactionService.getAllTransactionsWithPagination(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }




}

