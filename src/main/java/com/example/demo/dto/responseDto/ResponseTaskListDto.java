package com.example.demo.dto.responseDto;

import lombok.Data;

@Data
public class ResponseTaskListDto {
    private int propertyId;
    private Integer estimatedPrice;
    private Integer timeInDays;
    private Float cost;
    private String taskName;
}
