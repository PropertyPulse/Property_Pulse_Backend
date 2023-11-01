package com.example.demo.dto.responseDto;

import lombok.Data;

@Data
public class ResponseTaskListDto {
    private int noOfWorkers;
    private int timeInDays;
    private float cost;
    private int taskId;
}
