package com.example.demo.dto.responseDto;

import com.example.demo.entity.PropertyType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ResponseAddNewPropertyDto {
    private Integer id;
    private LocalDate accepted_date;
    private LocalDate returned_date;
    private LocalDate registered_date;
    private String address;
    private PropertyType type;
    private String location;
    private String district;
    private String duration;
    private Integer stories;
    private Integer bedrooms;
    private Integer living_rooms;
    private Integer bathrooms;
    private Integer dining_rooms;
    private String have_special_rooms;
    private String special_rooms;
    private Float land_size;
    private String have_crops;
    private String crops;
    private String special_facts;
    private String registered_status;
    private Integer property_owner;
    private Boolean want_insurance;
}
