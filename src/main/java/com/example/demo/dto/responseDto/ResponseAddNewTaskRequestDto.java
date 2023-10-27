package com.example.demo.dto.responseDto;

import com.example.demo.entity.Property;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseAddNewTaskRequestDto {
    private Integer propertyId;
    private String taskDescription;
    private LocalDate proposedStartDate;
    private String acceptedStatus;
    private String frequency;
    private String specialRemarks;
}
