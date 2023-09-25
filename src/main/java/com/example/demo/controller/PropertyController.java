package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestAddNewHomeDTO;
import com.example.demo.dto.requestDto.RequestAddNewLandDTO;
import com.example.demo.dto.requestDto.RequestAddNewPropertyDto;
import com.example.demo.dto.responseDto.ResponseAddNewPropertyDto;
import com.example.demo.entity.Property;
import com.example.demo.exception.UserException;
import com.example.demo.service.PropertyService;
import com.example.demo.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('PROPERTYOWNER')")
@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {
    private final PropertyService propertyService;


    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("/addNewProperty")
    @PreAuthorize("hasAuthority('propertyowner:create')")
    public ResponseEntity<String> addNewProperty(@RequestBody RequestAddNewPropertyDto req) throws UserException {

        try {
            propertyService.addNewProperty(req);
            return ResponseEntity.ok("Property registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error registering property: " + e.getMessage());
        }
    }

    /* @PreAuthorize("hasAuthority('propertyowner:create')")
    @PostMapping("/getAllProperties")
    public ResponseEntity<List<ResponseAddNewPropertyDto>> getAllProperties(@RequestBody RequestAddNewPropertyDto req) throws UserException {
        return propertyService.getAllProperties();
    }*/
}
