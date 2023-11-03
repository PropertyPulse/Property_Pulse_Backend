package com.example.demo.dto.responseDto;

import com.example.demo.entity.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ResponseNewManagementRequestDto {

    private Integer id;
    private List<String> checklist;
    private List<TaskRequest> taskRequests;
    private List<RecivedPayment> payablePayments;
    private boolean acceptedStatus;

    private LocalDate acceptedDate;
    private LocalDate returnedDate;
    private LocalDate registeredDate;
    private String address;
    private PropertyType type;
    private String location;
    private String assignStage;
    private String district;
    private String duration;
    private Integer stories;
    private Integer bedrooms;
    private Integer livingRooms;
    private Integer bathrooms;
    private String haveSpecialRooms;
    private String specialRooms;
    private Double landSize;
    private String haveCrops;
    private String crops;
    private String specialFacts;
    private String registeredStatus;
    private String visitStatus;
    private String priceListStatus;
    private String legalContractStatus;
    private String propertyOwnerContactNo;
    private Boolean wantInsurance;
    private List<TaskEquipmentPayment> equipmentPayments;
    private List<Document> files;
    private ValuationReport valuationReport;
    private String propertyOwnername;



}
