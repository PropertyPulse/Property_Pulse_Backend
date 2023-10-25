package com.example.demo.service;

import com.example.demo.dto.responseDto.FinancialManagerDashboardDataDto;
import com.example.demo.dto.responseDto.MonthlySummaryDto;
import com.example.demo.dto.responseDto.TransactionHistoryDto;
import com.example.demo.entity.TransactionHistory;
import com.example.demo.entity.TransactionType;
import com.example.demo.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional

public class TransactionHistoryServiceImpl implements TransactionHistoryService {


    private final TransactionHistoryRepository transactionHistoryRepository;
    @Autowired
    public TransactionHistoryServiceImpl(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }


    public Double getTotalBalance() {
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        Double totalIncome = 0.0;
        Double totalOutcome = 0.0;

        for (TransactionHistory transaction : transactionHistoryList) {
            if (transaction.getType() == TransactionType.INCOME) {
                totalIncome += transaction.getAmount();
            } else if (transaction.getType() == TransactionType.OUTCOME) {
                totalOutcome += transaction.getAmount();
            }
        }

        return totalIncome - totalOutcome;
    }
    @Override
    public Boolean insertIncome(Double amount, String description) {

        Double currentbalance = this.getTotalBalance();

        TransactionHistory transactionhistory = new TransactionHistory();
        transactionhistory.setAmount(amount);
        transactionhistory.setDescription(description);
        transactionhistory.setPaymentMethod("Online");
        Double newBalance = currentbalance + amount;
        transactionhistory.setBalance(newBalance);
        transactionhistory.setType(TransactionType.INCOME);

        TransactionHistory savedTransaction = transactionHistoryRepository.save(transactionhistory);
        return true;
    }

    @Override
    public Boolean insertExpense(Double amount, String description, String paymentMethod) {
        Double currentbalance = this.getTotalBalance();

        TransactionHistory transactionhistory = new TransactionHistory();
        transactionhistory.setAmount(amount);
        transactionhistory.setDescription(description);
        transactionhistory.setPaymentMethod(paymentMethod);
        Double newBalance = currentbalance - amount;
        transactionhistory.setBalance(newBalance);
        transactionhistory.setType(TransactionType.OUTCOME);

        TransactionHistory savedTransaction = transactionHistoryRepository.save(transactionhistory);
        return true;
    }

    @Override
    public List<TransactionHistoryDto> getAllTransactionHistory() {
        List<TransactionHistory> transactions = transactionHistoryRepository.findAll();
        return mapToDtoList(transactions);
    }

    @Override
    public List<MonthlySummaryDto> getMonthlyIncomeSummary() {
//        System.out.println("getMonthlyIncomeSummary() called");
        int year = LocalDate.now().getYear();
        List<MonthlySummaryDto> monthlyIncomeList = new ArrayList<>();

        List<TransactionHistory> transactions = transactionHistoryRepository.findIncomeTransactionsForCurrentYear();

        // Create a map to store monthly summaries
        Map<Integer, Double> monthlyTotals = new HashMap<>();

        // Initialize monthly totals to 0 for each month
        for (int month = 1; month <= 12; month++) {
            monthlyTotals.put(month, 0.0);
        }

        // Iterate through the transactions and update the monthly totals
        for (TransactionHistory transaction : transactions) {
            LocalDateTime transactionDate = transaction.getDate();
            int transactionMonth = transactionDate.getMonthValue();
            double transactionAmount = transaction.getAmount();

            // Update the monthly total for the transaction's month
            monthlyTotals.put(transactionMonth, monthlyTotals.get(transactionMonth) + transactionAmount);
        }

        // Create MonthlySummaryDto objects and add them to the result list
        for (int month = 1; month <= 12; month++) {
            double totalAmount = monthlyTotals.get(month);
            MonthlySummaryDto summaryDto = new MonthlySummaryDto();
            summaryDto.setMonth(month);
            summaryDto.setTotalAmount(totalAmount);
            monthlyIncomeList.add(summaryDto);
        }

        return monthlyIncomeList;
    }

    @Override
    public List<MonthlySummaryDto> getMonthlyExpenseSummary() {

//        System.out.println("getMonthlyExpenseSummary() called");
        int year = LocalDate.now().getYear();
        List<MonthlySummaryDto> monthlyOutcomeList = new ArrayList<>();
        List<TransactionHistory> transactions = transactionHistoryRepository.findOutcomeTransactionsForCurrentYear();

        // Create a map to store monthly summaries
        Map<Integer, Double> monthlyTotals = new HashMap<>();

        // Initialize monthly totals to 0 for each month
        for (int month = 1; month <= 12; month++) {
            monthlyTotals.put(month, 0.0);
        }

        // Iterate through the transactions and update the monthly totals
        for (TransactionHistory transaction : transactions) {
            LocalDateTime transactionDate = transaction.getDate();
            int transactionMonth = transactionDate.getMonthValue();
            double transactionAmount = transaction.getAmount();

            // Update the monthly total for the transaction's month
            monthlyTotals.put(transactionMonth, monthlyTotals.get(transactionMonth) + transactionAmount);
        }

        // Create MonthlySummaryDto objects and add them to the result list
        for (int month = 1; month <= 12; month++) {
            double totalAmount = monthlyTotals.get(month);
            MonthlySummaryDto summaryDto = new MonthlySummaryDto();
            summaryDto.setMonth(month);
            summaryDto.setTotalAmount(totalAmount);
            monthlyOutcomeList.add(summaryDto);
        }

        return monthlyOutcomeList;
    }

    @Override
    public List<FinancialManagerDashboardDataDto> getFMDashboardData() {

        List<FinancialManagerDashboardDataDto> financialManagerDashboardDataDtoList = new ArrayList<>();
        List<MonthlySummaryDto> incomes = this.getMonthlyIncomeSummary();
        List<MonthlySummaryDto> expenses = this.getMonthlyExpenseSummary();
        int year = LocalDate.now().getYear();
        for(int month = 1; month <= 12; month++) {
        	FinancialManagerDashboardDataDto financialManagerDashboardDataDto = new FinancialManagerDashboardDataDto();
        	financialManagerDashboardDataDto.setDate(year+"-"+month);
        	financialManagerDashboardDataDto.setUv(expenses.get(month-1).getTotalAmount());
        	financialManagerDashboardDataDto.setPv(incomes.get(month-1).getTotalAmount());
        	financialManagerDashboardDataDtoList.add(financialManagerDashboardDataDto);
        }
        return financialManagerDashboardDataDtoList;
    }

    @Override
    public Double getTotalIncome() {
        System.out.println("getTotalIncome() called");
        return transactionHistoryRepository.getTotalIncome();
    }

    @Override
    public Double getTotalOutcome() {
        System.out.println("getTotalOutcome() called");
        return transactionHistoryRepository.getTotalOutcome();
    }


    public List<TransactionHistoryDto> mapToDtoList(List<TransactionHistory> transactions) {
        return transactions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TransactionHistoryDto mapToDto(TransactionHistory transaction) {
        TransactionHistoryDto dto = new TransactionHistoryDto();
        dto.setId(transaction.getId().longValue());
        dto.setAmount(transaction.getAmount());
        dto.setDescription(transaction.getDescription());

        // Convert LocalDateTime to LocalDate
        LocalDateTime localDateTime = transaction.getDate();
        LocalDate localDate = localDateTime.toLocalDate();
        dto.setDate(localDate);

        dto.setPaymentMethod(transaction.getPaymentMethod());
        dto.setBalance(transaction.getBalance());
        dto.setType(transaction.getType().toString()); // Assuming TransactionType is an enum

        return dto;
    }
}
