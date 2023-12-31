package com.example.demo.controller;

import com.example.demo.dto.requestDto.PropertyVisitStatusRequestDto;
import com.example.demo.dto.requestDto.RequestTaskListDto;
import com.example.demo.dto.responseDto.ResponseAssignedPropertiesTSDto;
import com.example.demo.dto.responseDto.ResponsePropertiesToBeManagedDto;
import com.example.demo.dto.responseDto.ResponseTaskListDto;
import com.example.demo.exception.UserException;
import com.example.demo.service.AssignedPropertiesTSService;
import com.example.demo.service.PropertiesToBeManagedService;
import com.example.demo.service.TaskService;
import com.example.demo.service.UpdatePropertyVisitStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final TaskService taskService;

    private final UpdatePropertyVisitStatusService updatePropertyVisitStatusService;

    @Autowired
    public AssignedPropertiesTSController(AssignedPropertiesTSService assignedPropertiesTSService, PropertiesToBeManagedService propertiesToBeManagedService, TaskService taskService, UpdatePropertyVisitStatusService updatePropertyVisitStatusService) {
        this.assignedPropertiesTSService = assignedPropertiesTSService;
        this.propertiesToBeManagedService = propertiesToBeManagedService;
        this.taskService = taskService;
        this.updatePropertyVisitStatusService = updatePropertyVisitStatusService;
    }

    @GetMapping("/assignedPropertiesTS")
    public ResponseEntity<List<ResponseAssignedPropertiesTSDto>> getAssignedPropertiesForTaskSupervisor(@RequestParam("email") String email) throws UserException {
        List<ResponseAssignedPropertiesTSDto> assignedProperties = assignedPropertiesTSService.assignedPropertiesTS(email);
        return ResponseEntity.ok(assignedProperties);
    }

    @GetMapping("/propertiesToBeManged")
    public ResponseEntity<List<ResponsePropertiesToBeManagedDto>> getPropertiesToBeManagedForTaskSupervisor (@RequestParam("email") String email) throws UserException{
        List<ResponsePropertiesToBeManagedDto> propertiesToBeManaged = propertiesToBeManagedService.propertiesToBeManaged (email);
        return ResponseEntity.ok(propertiesToBeManaged);
    }

    @GetMapping("/propertiesToBeMangedVisited")
    public ResponseEntity<List<ResponsePropertiesToBeManagedDto>> getPropertiesToBeManaged_VisitedForTaskSupervisor (@RequestParam("email") String email) throws UserException{
        List<ResponsePropertiesToBeManagedDto> propertiesToBeManaged = propertiesToBeManagedService.propertiesToBeManagedVisited (email);
        return ResponseEntity.ok(propertiesToBeManaged);
    }

    @GetMapping("/getTaskList")
    public ResponseEntity<List<RequestTaskListDto>> getTaskList (@RequestParam("propertyId") Integer propertyId) throws UserException{
        List<RequestTaskListDto> tasksList = taskService.getTaskList (propertyId);
        return ResponseEntity.ok(tasksList);
    }

    @PostMapping("/updatePropertyVisitStatus")
    public ResponseEntity<String> updatePropertyVisitStatus(@RequestBody PropertyVisitStatusRequestDto request) {
        try {

            System.out.println("Received Property ID: " + request.getPropertyId());
            System.out.println("Received Visit Status: " + request.isVisitStatus());

            // Call the updateVisitStatus method from your service
            Boolean isUpdated = updatePropertyVisitStatusService.updateVisitStatus(request.getPropertyId(), request.isVisitStatus());

            if (isUpdated) {
                return ResponseEntity.ok("Visit status updated successfully.");
            } else {
                return ResponseEntity.badRequest().body("Failed to update visit status.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Replace this with proper error handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred.");
        }
    }

    @PostMapping("/setTaskList")
    public ResponseEntity<String> setTaskList(@RequestBody List<ResponseTaskListDto> responseTaskListDto) {
        try {

            Boolean isSetTask = taskService.setTaskList(responseTaskListDto);

            if (isSetTask) {
                return ResponseEntity.ok("Price List Updated");
            } else {
                return ResponseEntity.badRequest().body("Failed to update the price list");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred.");
        }
    }

}
