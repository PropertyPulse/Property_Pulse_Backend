package com.example.demo.dto.requestDto;

import com.example.demo.entity.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestAddNewInternalUserDto {

    private String userRole;
    private String firstName;
    private String lastName;
    private String address;
    private String nearestTown;
    private String district;
    private String contactNo;
    private String email;
    private String nic;
    private LocalDate dob;
    private Gender gender;
    private String password;

}
