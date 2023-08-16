package com.example.demo.dto.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseEmployeeDetailsDto {

//name, address, NIC, district,contactno, skills[]

    private Integer id;
    private String name;
    private String address;
    private String nic;
    private String contactno;
    private String district;
    private boolean isAssigned;
    private List<String> skills;



}
