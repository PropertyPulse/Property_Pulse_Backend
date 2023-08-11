package com.example.demo.dto.requestDto;

import lombok.Data;

import java.util.List;

@Data
public class RequestEmployeeDetailsDto {

//name, address, NIC, district,contactno, skills[]


    private String name;
    private String address;
    private String nic;
    private String contactno;
    private String district;

    private String email;
    private List<String> skills;




}
