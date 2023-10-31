package com.example.demo.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryDto {
    private Long id;
    private Double amount;
    private String description;
    private LocalDate date;
    private String paymentMethod;
    private Double balance;
    private String type;
}
