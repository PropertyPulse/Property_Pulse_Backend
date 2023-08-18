package com.example.demo.dto.requestDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestAddnewInternalUserDto {

    private String username;
    private String address;
    private String district;
    private Integer phone;
    private String email;
    private String password;
    private String nic;
    private LocalDate dob;



}
