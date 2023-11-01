package com.example.demo.dto.responseDto;


import com.example.demo.entity.PropertyType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseValuationPendingDTO {


    private Long propertyId;
    private PropertyType type;
    private String location;
    private String contact;
    private String firstname;
    private String lastname;
    private LocalDate requestedDate;
    private  String propertyAddress;
}
