package com.example.demo.dto.responseDto;

import lombok.Data;

@Data
public class ResponsePropertiesToBeManagedDto {
    private int propertyId;
    private String propertyOwnerName;
    private String address;
}