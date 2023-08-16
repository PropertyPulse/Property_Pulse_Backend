package com.example.demo.dto.requestDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestAssignDto {

//    feedback : feedback,
//    startDate : startDate,
//    requiredDate : requiredDate,
//    empid:selectedEmployeeId,
//    requestid:id

    private Integer id;
    private String feedback;
    private LocalDate startDate;
    private Integer requiredDate;
    private Integer empid;
    private Integer requestid;
}
