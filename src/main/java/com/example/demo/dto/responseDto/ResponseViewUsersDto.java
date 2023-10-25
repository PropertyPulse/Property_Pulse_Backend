package com.example.demo.dto.responseDto;

import com.example.demo.user.Role;
import lombok.Data;

@Data
public class ResponseViewUsersDto {

    private Integer userId;
    private String userName;
    private Role userRole;
    private String contactNo;
    private String email;

}
