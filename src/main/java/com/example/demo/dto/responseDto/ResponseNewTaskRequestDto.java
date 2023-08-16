package com.example.demo.dto.responseDto;

import com.example.demo.entity.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseNewTaskRequestDto {

    private Integer id;//taskRequest id
    private Integer propertyId;
    private String location;
    private String task;
    private Double estimatedPrice;
    private LocalDate scheduleDate;
    private String moreInfo;
    private TaskStatus taskStatus;


}
