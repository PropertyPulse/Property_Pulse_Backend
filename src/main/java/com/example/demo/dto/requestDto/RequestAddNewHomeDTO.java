package com.example.demo.dto.requestDto;

import lombok.Data;

@Data
public class RequestAddNewHomeDTO extends RequestAddNewPropertyDto{

    private Integer stories;
    private Integer bedrooms;
    private Integer living_rooms;
    private Integer bathrooms;


    private Integer dining_rooms;
    private String have_special_rooms;
    private String special_rooms;




}
