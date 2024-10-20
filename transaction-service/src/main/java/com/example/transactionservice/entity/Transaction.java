package com.example.transactionservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "trx_amount", nullable = false)
    private Double trxAmount;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "trx_date", nullable = false)
    private String trxDate;

    @Column(name = "trx_time", nullable = false)
    private String trxTime;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

}
