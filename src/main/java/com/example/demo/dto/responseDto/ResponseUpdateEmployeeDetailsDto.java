package com.example.demo.dto.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseUpdateEmployeeDetailsDto {

//name, address, NIC, district,contactno, skills[]

    private Integer id;
    private String name;
    private String address;
    private String nic;
    private String contactno;
    private String district;
    private List<String> skills;



}
