package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestUserDetailsDto;
import com.example.demo.dto.responseDto.ResponseAssignedPropertiesTSDto;
import com.example.demo.dto.responseDto.ResponsePropertiesToBeManagedDto;
import com.example.demo.exception.UserException;
import com.example.demo.service.AssignedPropertiesTSService;
import com.example.demo.service.PropertiesToBeManagedService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/TS")

@PreAuthorize("hasRole('TASKSUPERVISOR')")
public class AssignedPropertiesTSController {
    private final AssignedPropertiesTSService assignedPropertiesTSService;
    private final PropertiesToBeManagedService propertiesToBeManagedService;
    public AssignedPropertiesTSController(AssignedPropertiesTSService assignedPropertiesTSService, PropertiesToBeManagedService propertiesToBeManagedService) {
        this.assignedPropertiesTSService = assignedPropertiesTSService;
        this.propertiesToBeManagedService = propertiesToBeManagedService;
    }

//    @GetMapping("/assignedPropertiesTS")
//    public ResponseEntity<List<ResponseAssignedPropertiesTSDto>> getAssignedPropertiesForTaskSupervisor(@RequestParam("email") String email) throws UserException {
//        List<ResponseAssignedPropertiesTSDto> assignedProperties = assignedPropertiesTSService.assignedPropertiesTS(email);
//        return ResponseEntity.ok(assignedProperties);
//    }

    @GetMapping("/propertiesToBeManged")
    public ResponseEntity<List<ResponsePropertiesToBeManagedDto>> getPropertiesToBeManagedForTaskSupervisor (@RequestParam("email") String email) throws UserException{
        List<ResponsePropertiesToBeManagedDto> propertiesToBeManaged = propertiesToBeManagedService.propertiesToBeManaged (email);
        return ResponseEntity.ok(propertiesToBeManaged);
    }
}
