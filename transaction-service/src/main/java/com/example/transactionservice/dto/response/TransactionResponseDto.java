package com.example.transactionservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {

    private Long id;
    private String accountNumber;
    private Double trxAmount;
    private String description;
    private String trxDate;
    private String trxTime;
    private String customerId;
}
