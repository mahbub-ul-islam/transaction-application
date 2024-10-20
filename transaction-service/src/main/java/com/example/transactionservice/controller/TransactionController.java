package com.example.transactionservice.controller;

import com.example.transactionservice.dto.request.FilterRequestDto;
import com.example.transactionservice.dto.request.TransactionRequestDto;
import com.example.transactionservice.dto.response.TransactionResponseDto;
import com.example.transactionservice.exception.errors.resource.TransactionOptimisticLockException;
import com.example.transactionservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction API")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping()
    @Operation(summary = "Save Transaction")
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
        try {
            TransactionResponseDto transactionResponseDto = transactionService.updateTransaction(id, transactionRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDto);
        }catch (ObjectOptimisticLockingFailureException e) {
            log.error("OptimisticLockException: Transaction was modified by another user. {}", e.getMessage());
            throw new TransactionOptimisticLockException("Transaction was modified by another user. Please try again.");
        }
    }


    @PostMapping("/page")
    @Operation(summary = "get all transaction with pagination")
    public ResponseEntity<Page<TransactionResponseDto>> searchTransactions1(@RequestBody FilterRequestDto filterRequestDto) {
        log.info("Searching transactions with dto: {}", filterRequestDto);
        Page<TransactionResponseDto> page = transactionService.searchTransactions(filterRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}

