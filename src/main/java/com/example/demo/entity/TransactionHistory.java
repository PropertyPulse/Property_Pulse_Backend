package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Transaction_History")
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private TransactionType type;

    private String description;
    private Double amount;
    private Double balance;

    // Define a LocalDateTime field for the date and time
    private LocalDateTime date;


    private String paymentMethod;
    // Constructor, getters, and setters

    @PrePersist
    protected void onCreate() {
        // Set the date field to the current date and time before persisting
        date = LocalDateTime.now();
    }
}