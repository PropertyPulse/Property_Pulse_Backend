package com.example.demo.dto.responseDto;

import lombok.Data;

@Data
public class ResponsePropertiesToBeManagedDto {
    private int propertyId;
    private String propertyOwnerName;
    private String address;
    private String propertyType;
    private String visitStatus;
    private String priceListStatus;
    private String legalContractStatus;
}
