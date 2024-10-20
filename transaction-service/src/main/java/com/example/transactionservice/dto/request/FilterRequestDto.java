package com.example.transactionservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequestDto {

    private String customerId;
    private String accountNumber;
    private String description;
    private int page = 0;
    private int size = 10;
}
