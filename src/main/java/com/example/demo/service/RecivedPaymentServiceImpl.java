package com.example.demo.service;

import com.example.demo.dto.responseDto.ReceivedPaymentDto;
import com.example.demo.entity.RecivedPayment;
import com.example.demo.entity.RecivedPaymentType;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.RecivedPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class RecivedPaymentServiceImpl implements RecivedPaymentService {

    private final RecivedPaymentRepository recivedPaymentRepository;
    private final PropertyRepository propertyRepository;
    private final TransactionHistoryService transactionHistoryService;

    @Autowired
    public RecivedPaymentServiceImpl(RecivedPaymentRepository recivedPaymentRepository, PropertyRepository propertyRepository, TransactionHistoryService transactionHistoryService) {
        this.recivedPaymentRepository = recivedPaymentRepository;
        this.propertyRepository = propertyRepository;
        this.transactionHistoryService = transactionHistoryService;
    }

    public List<ReceivedPaymentDto> getAllMonthlyPayments() {
        List<RecivedPayment> monthlyPayments = recivedPaymentRepository.findByType(RecivedPaymentType.MONTHLYPAYMENTS);
        return mapToDtoList(monthlyPayments);
    }

    @Override
    public Boolean addMonthlyPayment(Integer propertyId, Double amount, String description) {
        RecivedPayment recivedPayment = new RecivedPayment();
        recivedPayment.setProperty(propertyRepository.findById(propertyId).get());
        recivedPayment.setAmount(amount);
        recivedPayment.setDescription(description);
        recivedPayment.setType(RecivedPaymentType.MONTHLYPAYMENTS);
//        set reciveddate as current date
        recivedPayment.setReceiveddate(java.time.LocalDate.now());

        RecivedPayment recivedPayment_saved =  recivedPaymentRepository.save(recivedPayment);
        transactionHistoryService.insertIncome(recivedPayment_saved.getAmount(),recivedPayment_saved.getDescription());
        return true;

    }

    @Override
    public Boolean addTaskPayment(Integer propertyId, Double amount, String description) {
        RecivedPayment recivedPayment = new RecivedPayment();
        recivedPayment.setProperty(propertyRepository.findById(propertyId).get());
        recivedPayment.setAmount(amount);
        recivedPayment.setDescription(description);
        recivedPayment.setType(RecivedPaymentType.SPECIALTASKPAYMENTS);
        recivedPayment.setReceiveddate(java.time.LocalDate.now());
        RecivedPayment recivedPayment_saved =  recivedPaymentRepository.save(recivedPayment);
        transactionHistoryService.insertIncome(recivedPayment_saved.getAmount(),recivedPayment_saved.getDescription());

        return true;
    }

    @Override
    public List<ReceivedPaymentDto> getAllTaskPayments() {
        List<RecivedPayment> spectialtaskpayements = recivedPaymentRepository.findByType(RecivedPaymentType.SPECIALTASKPAYMENTS);
        return mapToDtoList(spectialtaskpayements);
    }

    private List<ReceivedPaymentDto> mapToDtoList(List<RecivedPayment> payments) {
        return payments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ReceivedPaymentDto mapToDto(RecivedPayment payment) {
        ReceivedPaymentDto dto = new ReceivedPaymentDto();
        dto.setId(payment.getId());
        dto.setPropertyId(payment.getProperty().getId());
        dto.setAmount(payment.getAmount());
        dto.setDescription(payment.getDescription());
        dto.setReceiveddate(payment.getReceiveddate());
        // Set other fields as needed
        return dto;
    }
}