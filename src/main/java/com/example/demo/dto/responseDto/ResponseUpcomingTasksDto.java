package com.example.demo.dto.responseDto;

import lombok.Data;

@Data
public class ResponseUpcomingTasksDto {

    private Integer propertyId;
    private String location;
    private Integer taskId;
    private String task;
    private String requestStatus;
    private String taskStatus;

}
