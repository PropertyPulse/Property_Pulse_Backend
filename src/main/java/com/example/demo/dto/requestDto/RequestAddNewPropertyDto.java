package com.example.demo.dto.requestDto;

import com.example.demo.entity.PropertyType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class RequestAddNewPropertyDto {

    private String address;
    private PropertyType type;
    private String location;
    private String district;
    private String duration;

    private Boolean want_insurance;
    private List<MultipartFile> property_images
            ;
    private List<MultipartFile> insurance_documents
            ;


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

}
