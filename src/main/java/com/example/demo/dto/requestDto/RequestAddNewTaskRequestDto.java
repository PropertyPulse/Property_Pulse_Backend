package com.example.demo.dto.requestDto;

import com.example.demo.entity.Property;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestAddNewTaskRequestDto {
    private Integer propertyId;
    private String taskDescription;
    private LocalDate proposedStartDate;
    private String acceptedStatus;
    private String frequency;
    private String specialRemarks;
}
