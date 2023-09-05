package com.example.demo.dto.requestDto;

import lombok.Data;

@Data
public class PaymentRequestDto {

    private Integer propertyId;
    private Double amount;
    private String description;

}

