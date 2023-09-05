//package com.example.demo.service;
//
//import com.example.demo.dto.responseDto.MonthlyPaymentDto;
//import com.example.demo.entity.ManpowerCompanyPayments;
//import com.example.demo.repository.ManpowerCompanyPaymentsRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.YearMonth;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//@Service
//@Transactional
//
//public class ManpowerCompanyPaymentsServiceImpl implements ManpowerCompanyPaymentsService {
//
//    private final ManpowerCompanyPaymentsRepository paymentsRepository;
//
//    public ManpowerCompanyPaymentsServiceImpl(ManpowerCompanyPaymentsRepository paymentsRepository) {
//        this.paymentsRepository = paymentsRepository;
//    }
//
//    public List<MonthlyPaymentDto> getTotalUnpaidPaymentsPerMonth() {
//        List<ManpowerCompanyPayments> unpaidPayments = paymentsRepository.findByIsPaidFalse();
//
//        Map<YearMonth, MonthlyPaymentDto> monthlyPaymentsMap = new HashMap<>();
//
//        for (ManpowerCompanyPayments payment : unpaidPayments) {
//            YearMonth yearMonth = YearMonth.from(payment.getDate());
//            MonthlyPaymentDto monthlyPaymentDTO = monthlyPaymentsMap.getOrDefault(yearMonth, new MonthlyPaymentDto());
//
//            monthlyPaymentDTO.setPaymentId(payment.getId());
//            monthlyPaymentDTO.setPaymentMonth(yearMonth);
//
//            // Increment total amount
//            Double totalAmount = monthlyPaymentDTO.getTotalAmount() != null ? monthlyPaymentDTO.getTotalAmount() : 0.0;
//            totalAmount += payment.getAmount();
//            monthlyPaymentDTO.setTotalAmount(totalAmount);
//
//            // Calculate due date as the end of the month
//            LocalDate lastDayOfMonth = yearMonth.atDay(yearMonth.lengthOfMonth());
//            YearMonth dueDate = YearMonth.from(lastDayOfMonth);
//            monthlyPaymentDTO.setDueDate(dueDate);
//            System.out.println(dueDate);
//            monthlyPaymentsMap.put(yearMonth, monthlyPaymentDTO);
//        }
//
//        return new ArrayList<>(monthlyPaymentsMap.values());
//    }
//}

package com.example.demo.service;

import com.example.demo.dto.responseDto.MonthlyPaymentDto;
import com.example.demo.entity.ManpowerCompanyPayments;
import com.example.demo.repository.ManpowerCompanyPaymentsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
@Transactional
public class ManpowerCompanyPaymentsServiceImpl implements ManpowerCompanyPaymentsService {

    private final ManpowerCompanyPaymentsRepository paymentsRepository;

    private final TransactionHistoryService transactionHistoryService;

    public ManpowerCompanyPaymentsServiceImpl(ManpowerCompanyPaymentsRepository paymentsRepository, TransactionHistoryService transactionHistoryService) {
        this.paymentsRepository = paymentsRepository;
        this.transactionHistoryService = transactionHistoryService;
    }

    public List<MonthlyPaymentDto> getTotalUnpaidPaymentsPerMonth() {
        List<ManpowerCompanyPayments> unpaidPayments = paymentsRepository.findByIsPaidFalse();

        Map<YearMonth, MonthlyPaymentDto> monthlyPaymentsMap = new HashMap<>();

        for (ManpowerCompanyPayments payment : unpaidPayments) {
            YearMonth yearMonth = YearMonth.from(payment.getDate());
            MonthlyPaymentDto monthlyPaymentDTO = monthlyPaymentsMap.getOrDefault(yearMonth, new MonthlyPaymentDto());

            monthlyPaymentDTO.getPaymentIds().add(payment.getId()); // Add the payment ID to the list

            monthlyPaymentDTO.setPaymentMonth(yearMonth);

            // Increment total amount
            Double totalAmount = monthlyPaymentDTO.getTotalAmount() != null ? monthlyPaymentDTO.getTotalAmount() : 0.0;
            totalAmount += payment.getAmount();
            monthlyPaymentDTO.setTotalAmount(totalAmount);

            // Calculate due date as the end of the month
            LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
            monthlyPaymentDTO.setDueDate(lastDayOfMonth);

            monthlyPaymentsMap.put(yearMonth, monthlyPaymentDTO);
        }

        return new ArrayList<>(monthlyPaymentsMap.values());
    }

    @Override
    public Boolean updatePaymentStatus(MonthlyPaymentDto monthlyPaymentDto) {
        try {
            // Get the list of payment IDs from the MonthlyPaymentDto
            List<Integer> paymentIds = monthlyPaymentDto.getPaymentIds();

            // Assuming you have a service or repository to update payment status
            // You can iterate through the payment IDs and update their status
            Double totalamount = 0.0;

            for (Integer paymentId : paymentIds) {
                // Fetch the payment entity by ID (you need to implement this)
                Optional<ManpowerCompanyPayments> payment = paymentsRepository.findById(paymentId);
                ManpowerCompanyPayments payment1 = payment.get();

                // Check if the payment exists and set its isPaid status to true
                if (payment1 != null) {
                    payment1.setPaid(true);

                   totalamount += payment1.getAmount();

                    // Update the payment entity (you need to implement this)
                    paymentsRepository.save(payment1);
                } else {
                    // Handle the case where the payment with the given ID doesn't exist
                    // You can throw an exception or log an error message
                }

            }

//            System.out.println(totalamount);

            transactionHistoryService.insertExpense(totalamount,monthlyPaymentDto.getDescription(), monthlyPaymentDto.getPaymentMethod());

            return true; // Update successful
        } catch (Exception e) {
            // Handle any exceptions that may occur during the update process
            e.printStackTrace(); // Replace this with proper error handling
            return false; // Update failed
        }
    }


}
