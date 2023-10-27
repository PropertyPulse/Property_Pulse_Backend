package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestUserDetailsDto;
import com.example.demo.dto.responseDto.ResponseGetAllPropertiesByUserDto;
import com.example.demo.exception.UserException;
import com.example.demo.service.GetAllPropertiesPOService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/PO")
@PreAuthorize("hasRole('PROPERTYOWNER')")
public class GetPropertiesByUserController {
    private final GetAllPropertiesPOService getAllPropertiesPOService;

    public GetPropertiesByUserController(GetAllPropertiesPOService getAllPropertiesPOService) {
        this.getAllPropertiesPOService = getAllPropertiesPOService;
    }

    @GetMapping("/getAllPropertiesPO")
    public ResponseEntity<List<ResponseGetAllPropertiesByUserDto>> getAllPropertiesByUser(@RequestBody RequestUserDetailsDto email) throws UserException {
        List<ResponseGetAllPropertiesByUserDto> allProperties = getAllPropertiesPOService.getAllPropertiesPO(email.getEmail());
        return ResponseEntity.ok(allProperties);
    }
}
