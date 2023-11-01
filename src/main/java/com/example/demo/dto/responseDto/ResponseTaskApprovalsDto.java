package com.example.demo.dto.responseDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseTaskApprovalsDto {

    private Integer propertyId;
    private String address;
    private Integer taskId;
    private String task;
    private LocalDate startDate;
    private String manpowerCompanyRequestStatus;

}
