package com.example.demo.service;

import com.example.demo.dto.responseDto.TransactionHistoryDto;
import com.example.demo.entity.TransactionHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface TransactionHistoryService {

    Boolean insertIncome(Double amount, String description);
    Boolean insertExpense(Double amount, String description ,String paymentMethod);

    List<TransactionHistoryDto>getAllTransactionHistory();

}
