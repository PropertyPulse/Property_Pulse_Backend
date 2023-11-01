package com.example.demo.dto.responseDto;

import com.example.demo.entity.TaskSupervisor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseGetAllPropertiesByUserDto {
    private Integer propertyId;
    private String address;
    private String district;
    private String propertyType;
    private String duration;
    private TaskSupervisor taskSupervisor;
    private String registeredStatus;
    private LocalDate registeredDate;

}
