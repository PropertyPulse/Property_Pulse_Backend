package com.example.demo.dto.requestDto;

import lombok.Data;

@Data
public class RequestAddContactPersonDto {

    private int taskId;
    private String contactPersonName;
    private String contactPersonNumber;

}
