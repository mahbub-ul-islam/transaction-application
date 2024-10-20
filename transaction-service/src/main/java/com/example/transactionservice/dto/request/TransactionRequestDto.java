package com.example.transactionservice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {

    @NotEmpty(message = "Account number is required")
    private String accountNumber;

    @DecimalMin(value = "0.0", inclusive = false, message = "Transaction amount must be positive")
    private Double trxAmount;

    private String description;

    @NotNull(message = "Transaction date is required")
    private String trxDate;

    @NotNull(message = "Transaction time is required")
    private String trxTime;

    @NotEmpty(message = "Customer ID is required")
    private String customerId;
}
