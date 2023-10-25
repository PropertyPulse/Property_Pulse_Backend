package com.example.demo.repository;

import com.example.demo.entity.TransactionHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {
    @Query("SELECT th FROM TransactionHistory th WHERE YEAR(th.date) = :year")
    List<TransactionHistory> findByYear(int year);
    @Query("SELECT th FROM TransactionHistory th WHERE YEAR(th.date) = YEAR(CURRENT_DATE) AND th.type = 0")
    List<TransactionHistory> findIncomeTransactionsForCurrentYear();
    // Function to retrieve OUTCOME transactions for the current year
    @Query("SELECT th FROM TransactionHistory th WHERE YEAR(th.date) = YEAR(CURRENT_DATE) AND th.type = 1")
    List<TransactionHistory> findOutcomeTransactionsForCurrentYear();

    @Query("SELECT SUM(t.amount) FROM TransactionHistory t WHERE t.type = 0")
    Double getTotalIncome();

    // Method to calculate the total outcome
    @Query("SELECT SUM(t.amount) FROM TransactionHistory t WHERE t.type = 1")
    Double getTotalOutcome();

}
