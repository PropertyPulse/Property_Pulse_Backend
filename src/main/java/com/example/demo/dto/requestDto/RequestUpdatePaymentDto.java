package com.example.demo.dto.requestDto;

import lombok.Data;

@Data
public class RequestUpdatePaymentDto {


    private Integer id;
    private String paymentMethod;
    private String description;
}
