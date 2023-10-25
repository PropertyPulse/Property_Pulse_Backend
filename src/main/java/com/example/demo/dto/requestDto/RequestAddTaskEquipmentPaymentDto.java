package com.example.demo.dto.requestDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestAddTaskEquipmentPaymentDto {
    private Integer property_id;
    private Integer taskSupervisor_id;

    private Double amount;

    private String description;

    private LocalDate date;

    private boolean isPaid;
}
