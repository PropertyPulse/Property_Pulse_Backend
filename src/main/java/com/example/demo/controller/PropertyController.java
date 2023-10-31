package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestAddNewPropertyDto;
import com.example.demo.dto.responseDto.ResponseAddNewPropertyDto;
import com.example.demo.dto.responseDto.ResponseGetAllPropertiesByUserDto;
import com.example.demo.entity.Property;
import com.example.demo.exception.UserException;
import com.example.demo.service.PropertyService;
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
    public ResponseEntity<ResponseAddNewPropertyDto> addNewProperty(@RequestBody RequestAddNewPropertyDto req) throws UserException {
        ResponseAddNewPropertyDto savedProperty = propertyService.addNewProperty(req);
        ResponseAddNewPropertyDto responseDto = new ResponseAddNewPropertyDto();
        responseDto.setId(savedProperty.getId());
        return ResponseEntity.ok(responseDto);
    }

    // @PreAuthorize("hasAuthority('propertyowner:read')")
    @GetMapping("/get-all-properties-by-owner")
    public ResponseEntity<List<ResponseGetAllPropertiesByUserDto>> getAllProperties(@RequestParam("email") String email) throws UserException {
        List<ResponseGetAllPropertiesByUserDto> allProperties = propertyService.getAllPropertiesByUser(email);
        return ResponseEntity.ok(allProperties);
    }


    @GetMapping("/get-property-by-id")
    @PreAuthorize("hasAuthority('propertyowner:read')")
    public ResponseEntity<ResponseAddNewPropertyDto> getPropertyById(@RequestParam("id") Integer id) {
        ResponseAddNewPropertyDto property = propertyService.getPropertyById(id);
        return ResponseEntity.ok(property);
    }
}
