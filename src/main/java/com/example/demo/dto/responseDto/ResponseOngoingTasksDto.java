package com.example.demo.dto.responseDto;

import lombok.Data;

@Data
public class ResponseOngoingTasksDto {

    private Integer propertyId;
    private String location;
    private Integer taskId;
    private String manpowerCompany;
    private String taskStatus;

}
