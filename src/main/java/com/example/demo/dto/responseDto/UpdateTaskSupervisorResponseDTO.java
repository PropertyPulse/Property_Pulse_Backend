package com.example.demo.dto.responseDto;



import lombok.Data;

@Data
public class UpdateTaskSupervisorResponseDTO {



        private Integer propertyId;
        private String location;
        private String name; // Task Supervisor's name
        private int ongoingTask; // Number of ongoing tasks related to the property


    }


