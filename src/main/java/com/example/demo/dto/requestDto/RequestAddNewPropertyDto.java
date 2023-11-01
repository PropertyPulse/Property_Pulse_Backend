package com.example.demo.dto.requestDto;

import com.example.demo.entity.PropertyType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class RequestAddNewPropertyDto {
    private LocalDate accepted_date;
    private LocalDate returned_date;
    private LocalDate registered_date;
    private String address;
    private PropertyType type;
    private String location;
    private String district;
    private String duration;
    private Integer property_owner_id;
    private String registered_status;
    private Boolean accepted_status;

    private List<MultipartFile> propertyImages;
    private List<MultipartFile> propertyDocuments;


//    private Integer stories;
//    private Integer bedrooms;
//    private Integer living_rooms;
//    private Integer bathrooms;
//    private Integer dining_rooms;
//    private String have_special_rooms;
//    private String special_rooms;
//    private Float land_size;
//    private String have_crops;
//    private String crops;
//    private String special_facts;
//    private String registered_status;
//    private String property_owner_email;
//    private Boolean want_insurance;

    private ArrayList<String> checklist;


}
