package com.example.demo.service;

import com.example.demo.dto.responseDto.MonthlyPaymentDto;
import com.example.demo.dto.responseDto.ReceivedPaymentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface RecivedPaymentService {

    List<ReceivedPaymentDto> getAllMonthlyPayments();

    Boolean addMonthlyPayment(Integer propertyId, Double amount, String description);
    Boolean addTaskPayment(Integer propertyId, Double amount, String description);
    List<ReceivedPaymentDto> getAllTaskPayments();



}
