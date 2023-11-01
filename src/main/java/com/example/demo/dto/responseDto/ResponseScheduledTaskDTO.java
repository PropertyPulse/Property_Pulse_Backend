package com.example.demo.dto.responseDto;


import com.example.demo.entity.Property;
import lombok.Data;

@Data
public class ResponseScheduledTaskDTO {


    private String type;
    private String frequency;
    private Property property;
    private String task;
    private Integer OngoingNoOfTasks;

}
