package com.example.demo.service;


import com.example.demo.dto.requestDto.RequestAddTaskEquipmentPaymentDto;
import com.example.demo.dto.requestDto.RequestUpdatePaymentDto;
import com.example.demo.dto.responseDto.MonthlyPaymentDto;
import com.example.demo.dto.responseDto.ResponseTaskEquipPaymentsDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface TaskEquipmentPaymentService {

    List<ResponseTaskEquipPaymentsDto> getAllTaskEquipmentpayments();

    Boolean updatePaymentStatus(RequestUpdatePaymentDto payment);

    Boolean addTaskEquipmentPayment(RequestAddTaskEquipmentPaymentDto req);

}
