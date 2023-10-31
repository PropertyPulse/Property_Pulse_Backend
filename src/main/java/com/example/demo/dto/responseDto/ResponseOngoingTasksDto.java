package com.example.demo.dto.responseDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseOngoingTasksDto {

    private Integer propertyId;
    private String location;
    private Integer taskId;
    private String task;
    private String manpowerCompany;
    private String taskStatus;
    private LocalDate startedDate;
    private LocalDate endingDate;

}
