package com.example.transactionservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

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
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String trxDate;

    @NotNull(message = "Transaction time is required")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
//    @Schema(type = "string", pattern = "HH:mm:ss", example = "12:00:00")
    private String trxTime;

    @NotEmpty(message = "Customer ID is required")
    private String customerId;
}
