package com.example.transactionservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagedResponse<T> {

    private List<T> data;
    private int pageNumber;
    private int pageSize;
    private long totalPages;
    private int totalElements;
}
