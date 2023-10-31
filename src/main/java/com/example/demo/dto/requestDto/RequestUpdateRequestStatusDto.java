package com.example.demo.dto.requestDto;

import lombok.Data;

@Data
public class RequestUpdateRequestStatusDto {

    private int taskId;
    private String requestStatus;

}
