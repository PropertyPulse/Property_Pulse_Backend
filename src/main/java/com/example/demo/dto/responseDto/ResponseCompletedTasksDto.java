package com.example.demo.dto.responseDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseCompletedTasksDto {

    private Integer propertyId;
    private Integer taskId;
    private String task;
    private String startDate;
    private LocalDate endDate;
    private String taskStatus;

}
