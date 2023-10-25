package com.example.demo.service;

import com.example.demo.dto.responseDto.TransactionHistoryDto;
import com.example.demo.entity.TransactionHistory;
import com.example.demo.entity.TransactionType;
import com.example.demo.repository.TransactionHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;

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
