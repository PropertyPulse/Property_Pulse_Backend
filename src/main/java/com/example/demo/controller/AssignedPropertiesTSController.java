package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestUserdetails;
import com.example.demo.dto.responseDto.ResponseAssignedPropertiesTSDto;
import com.example.demo.exception.UserException;
import com.example.demo.service.AssignedPropertiesTSService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/TS")

@PreAuthorize("hasRole('TASKSUPERVISOR')")
public class AssignedPropertiesTSController {
    private final AssignedPropertiesTSService assignedPropertiesTSService;
    public AssignedPropertiesTSController(AssignedPropertiesTSService assignedPropertiesTSService) {
        this.assignedPropertiesTSService = assignedPropertiesTSService;
    }

    @GetMapping("/assignedPropertiesTS")
    public ResponseEntity<List<ResponseAssignedPropertiesTSDto>> getAssignedPropertiesForTaskSupervisor(@RequestBody RequestUserdetails email) throws UserException {
        List<ResponseAssignedPropertiesTSDto> assignedProperties = assignedPropertiesTSService.assignedPropertiesTS(email.getEmail());
        return ResponseEntity.ok(assignedProperties);
    }
}
