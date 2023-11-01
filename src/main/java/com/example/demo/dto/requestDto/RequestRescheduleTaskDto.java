package com.example.demo.dto.requestDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestRescheduleTaskDto {

    private int taskId;
    private LocalDate startDate;
    private LocalDate endDate;

}
