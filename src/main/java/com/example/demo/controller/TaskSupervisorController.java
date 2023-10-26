package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestUserDetailsDto;
import com.example.demo.dto.responseDto.*;
import com.example.demo.service.TaskSupervisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/ts")

@PreAuthorize("hasRole('TASKSUPERVISOR')")
public class TaskSupervisorController {

    private final TaskSupervisorService taskSupervisorService;

    public TaskSupervisorController(TaskSupervisorService taskSupervisorService) {
        this.taskSupervisorService = taskSupervisorService;
    }

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('customer:read')")
//    public ResponseEntity<String> get(){
//        // Replace the following string with an actual object you want to return as JSON
//        // You can create a class representing the response data and return an instance of that class.
//        String responseData = "GET :: CUSTOMER";
//        return ResponseEntity.ok(responseData);
//    }

//    @PostMapping
//    @PreAuthorize("hasAuthority('customer:create')")
//    public String post(){
//        return "POST :: CUSTOMER";
//    }


    @PostMapping("/tsdetails")
    @PreAuthorize("hasAuthority('tasksupervisor:read')")
    public ResponseEntity<ResponseTsDetailsDto> getTsdetails(@RequestBody final RequestUserDetailsDto requestUserdetails){

        ResponseTsDetailsDto responseTsdetailsDto;
        responseTsdetailsDto = taskSupervisorService.getTasksupervisorDetails(requestUserdetails);

        return ResponseEntity.ok(responseTsdetailsDto);
    }

}