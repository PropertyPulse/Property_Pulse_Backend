package com.example.demo.dto.responseDto;

import lombok.Data;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Data
public class MonthlyPaymentDto {
    private List<Integer> paymentIds; // List of payment IDs
    private YearMonth paymentMonth;
    private Double totalAmount;
    private LocalDate dueDate;

    private String paymentMethod;
    private String description;

    // Constructor initializes paymentIds list
    public MonthlyPaymentDto() {
        this.paymentIds = new ArrayList<>();
    }

    // Getters and setters for other properties

    public List<Integer> getPaymentIds() {
        return paymentIds;
    }

    public void setPaymentIds(List<Integer> paymentIds) {
        this.paymentIds = paymentIds;
    }
}
