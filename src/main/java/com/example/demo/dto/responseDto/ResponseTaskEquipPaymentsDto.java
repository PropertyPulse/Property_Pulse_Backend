package com.example.demo.dto.responseDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseTaskEquipPaymentsDto {

    private Integer paymentId; // List of payment IDs
    private LocalDate date;
    private Double amount;
    private String description;
    private Integer propertyId;

    private Integer taskSupervisorId;
    private String taskSupervisorName;


}
