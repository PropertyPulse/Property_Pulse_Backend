package com.example.demo.dto.requestDto;


import lombok.Data;

@Data
public class RequestAddNewLandDTO extends RequestAddNewPropertyDto {

    private Float land_size;
    private String have_crops
            ;
    private String crops;
    private String specialFacts;


}
