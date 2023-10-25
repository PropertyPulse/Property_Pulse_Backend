package com.example.demo.dto.responseDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionHistoryDto {
    private Long id;
    private Double amount;
    private String description;
    private LocalDate date;
    private String paymentMethod;
    private Double balance;
    private String type;
}
