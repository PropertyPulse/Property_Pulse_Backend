package com.example.demo.service;

import com.example.demo.dto.responseDto.MonthlyPaymentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ManpowerCompanyPaymentsService {

    List<MonthlyPaymentDto> getTotalUnpaidPaymentsPerMonth() ;

    Boolean updatePaymentStatus(MonthlyPaymentDto monthlyPaymentDto);

}
