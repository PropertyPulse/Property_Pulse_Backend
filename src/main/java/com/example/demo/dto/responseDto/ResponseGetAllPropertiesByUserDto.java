package com.example.demo.dto.responseDto;

import lombok.Data;

@Data
public class ResponseGetAllPropertiesByUserDto {
    private Integer propertyId;
    private String address;
    private String district;
    private String propertyType;
}
