package com.example.demo.dto.responseDto;

import lombok.Data;

@Data
public class ResponseAssignedPropertiesTSDto {
    private Integer propertyId;
    private String propertyOwner;
    private String propertyType;
    private String address;
}